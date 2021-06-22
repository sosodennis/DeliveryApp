package com.dw.deliveryapp.di

import android.app.Application
import com.dw.deliveryapp.data.dao.DeliveryDao
import com.dw.deliveryapp.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return AppDatabase.getInstance(application)
    }

    @Provides
    @Singleton
    fun provideDeliveryDao(appDatabase: AppDatabase): DeliveryDao {
        return appDatabase.deliveryDao()
    }
}