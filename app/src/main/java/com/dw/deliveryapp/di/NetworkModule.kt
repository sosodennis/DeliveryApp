package com.dw.deliveryapp.di

import com.dw.deliveryapp.api.DeliveryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideDeliveryService(): DeliveryService {
        return DeliveryService.create()
    }
}