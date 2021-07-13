package com.dw.deliveryapp.data.repository

import com.dw.deliveryapp.data.db.AppDatabase
import com.dw.deliveryapp.data.model.FavoriteDelivery
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class FavoriteDeliveryRepository @Inject constructor(
    private val appDatabase: AppDatabase
) {

    fun isFavoriteFlow(id: String): Flow<FavoriteDelivery?> {
        return appDatabase.favoriteDeliveryDao().getFavorDeliveryByIdFlow(id)
    }

    fun isFavorite(id: String): FavoriteDelivery? {
        return appDatabase.favoriteDeliveryDao().getFavorDeliveryById(id)
    }

    suspend fun favorite(id: String) {
        appDatabase.favoriteDeliveryDao().insert(FavoriteDelivery(id))
    }

    suspend fun unfavorite(id: String) {
        appDatabase.favoriteDeliveryDao().delete(id)
    }
}