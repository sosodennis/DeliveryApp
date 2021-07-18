package com.dw.deliveryapp.data.repository

import androidx.room.withTransaction
import com.dw.deliveryapp.data.db.AppDatabase
import com.dw.deliveryapp.data.model.FavoriteDelivery
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class FavoriteDeliveryRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase
) : FavoriteDeliveryRepository {
    override fun favoriteStateFlow(id: String): Flow<Boolean> {
        return appDatabase.favoriteDeliveryDao().getFavorStateByIdFlow(id)
    }

    override fun isFavorite(id: String): Boolean {
        return appDatabase.favoriteDeliveryDao().isIdExist(id)
    }

    override suspend fun setFavoriteState(id: String, isFav: Boolean) {
        appDatabase.withTransaction {
            if (isFav) {
                appDatabase.favoriteDeliveryDao().insert(FavoriteDelivery(id))
            } else {
                appDatabase.favoriteDeliveryDao().delete(id)
            }
            appDatabase.deliveryDao().setFavorite(id, isFav)
        }
    }
}