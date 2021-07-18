package com.dw.deliveryapp.di

import android.content.Context
import androidx.room.Room
import com.dw.deliveryapp.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestDatabaseModule {
    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .setTransactionExecutor(Executors.newSingleThreadExecutor())
            .build()

    @Provides
    fun provideDeliveryDao(@Named("test_db") appDatabase: AppDatabase) =
        appDatabase.deliveryDao()

    @Provides
    fun provideFavroitDeliveryDao(@Named("test_db") appDatabase: AppDatabase) =
        appDatabase.favoriteDeliveryDao()
}