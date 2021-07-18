package com.dw.deliveryapp.di

import com.dw.deliveryapp.data.db.AppDatabase
import com.dw.deliveryapp.data.repository.DeliveryRepository
import com.dw.deliveryapp.data.repository.FakeDeliveryRepositoryImpl
import com.dw.deliveryapp.data.repository.FakeFavoriteDeliveryRepositoryImpl
import com.dw.deliveryapp.data.repository.FavoriteDeliveryRepository
import com.dw.deliveryapp.ui.paging.DeliveryRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
    @Provides
    @Singleton
    @Named("test_delivery_repository")
    fun provideDeliveryRepository(
        @Named("test_db") appDatabase: AppDatabase,
        @Named("test_remote_mediator") remoteMediator: DeliveryRemoteMediator
    ) = FakeDeliveryRepositoryImpl(appDatabase, remoteMediator) as DeliveryRepository

    @Provides
    @Singleton
    @Named("test_favorite_delivery_repository")
    fun provideFavoriteDeliveryRepository(
        @Named("test_db") appDatabase: AppDatabase,
    ) = FakeFavoriteDeliveryRepositoryImpl(appDatabase) as FavoriteDeliveryRepository
}