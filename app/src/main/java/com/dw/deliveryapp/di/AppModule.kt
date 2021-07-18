package com.dw.deliveryapp.di

import android.app.Application
import android.content.res.Resources
import com.dw.deliveryapp.data.db.AppDatabase
import com.dw.deliveryapp.data.repository.DeliveryRepository
import com.dw.deliveryapp.data.repository.DeliveryRepositoryImpl
import com.dw.deliveryapp.data.repository.FavoriteDeliveryRepository
import com.dw.deliveryapp.data.repository.FavoriteDeliveryRepositoryImpl
import com.dw.deliveryapp.ui.paging.DeliveryRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppResources(application: Application): Resources {
        return application.resources
    }

    @Provides
    @Singleton
    fun provideDeliveryRepository(
        appDatabase: AppDatabase,
        remoteMediator: DeliveryRemoteMediator
    ) = DeliveryRepositoryImpl(appDatabase, remoteMediator) as DeliveryRepository

    @Provides
    @Singleton
    fun provideFavoriteDeliveryRepository(
        appDatabase: AppDatabase,
    ) = FavoriteDeliveryRepositoryImpl(appDatabase) as FavoriteDeliveryRepository
}