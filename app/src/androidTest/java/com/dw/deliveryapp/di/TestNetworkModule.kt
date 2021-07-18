package com.dw.deliveryapp.di

import com.dw.deliveryapp.api.DeliveryService
import com.dw.deliveryapp.data.dto.DeliveryDto
import com.dw.deliveryapp.util.readJsonFromResources
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestNetworkModule {
    class FakeDeliveryService : DeliveryService {
        enum class MockResult {
            MORE_DATA,
            NO_MORE_DATA,
            ERROR
        }

        var expectedMockResult = MockResult.MORE_DATA

        fun setMockResult(mockResult: MockResult) {
            expectedMockResult = mockResult
        }

        override suspend fun getDeliveries(offset: Int, limit: Int): List<DeliveryDto> {
            val data: List<DeliveryDto>
            val gson = Gson()
            val deliveryDtoType = object : TypeToken<List<DeliveryDto>>() {}.type
            data = when (expectedMockResult) {
                MockResult.MORE_DATA -> {
                    val json = readJsonFromResources(this.javaClass, "delivery_list.json")
                    gson.fromJson(json, deliveryDtoType)
                }
                MockResult.NO_MORE_DATA -> {
                    listOf()
                }
                MockResult.ERROR -> {
                    gson.fromJson("", deliveryDtoType)
                }
            }
            return data
        }
    }

    @Provides
    @Named("test_delivery_service")
    fun provideDeliveryService(): FakeDeliveryService {
        return FakeDeliveryService()
    }
}