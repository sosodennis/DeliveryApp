package com.dw.deliveryapp.di

import com.dw.deliveryapp.data.mapper.DeliveryMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataMapperModule {
    @Singleton
    @Provides
    fun provideDeliveryMapper(): DeliveryMapper {
        return DeliveryMapper()
    }
}