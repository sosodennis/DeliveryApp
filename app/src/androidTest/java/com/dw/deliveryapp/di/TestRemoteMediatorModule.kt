package com.dw.deliveryapp.di

import com.dw.deliveryapp.data.db.AppDatabase
import com.dw.deliveryapp.data.mapper.DeliveryMapper
import com.dw.deliveryapp.ui.paging.DeliveryRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
@Named("test_remote_mediator")
object TestRemoteMediatorModule {
    @Provides
    @Named("test_remote_mediator")
    fun provideDeliveryRemoteMediator(
        @Named("test_db") appDatabase: AppDatabase,
        @Named("test_delivery_service") deliveryService: TestNetworkModule.FakeDeliveryService,
        mapper: DeliveryMapper
    ) = DeliveryRemoteMediator(appDatabase, deliveryService, mapper)
}