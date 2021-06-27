package com.dw.deliveryapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dw.deliveryapp.data.dao.DeliveryDao
import com.dw.deliveryapp.data.dao.DeliveryRemoteKeyDao
import com.dw.deliveryapp.data.dao.FavoriteDeliveryDao
import com.dw.deliveryapp.data.model.Delivery
import com.dw.deliveryapp.data.model.DeliveryRemoteKey
import com.dw.deliveryapp.data.model.FavoriteDelivery

@Database(
    entities = [
        Delivery::class,
        DeliveryRemoteKey::class,
        FavoriteDelivery::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun deliveryDao(): DeliveryDao
    abstract fun deliveryRemoteKeyDao(): DeliveryRemoteKeyDao
    abstract fun favoriteDeliveryDao(): FavoriteDeliveryDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabaseConfig.DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}