package com.dw.deliveryapp.data.repository

import kotlinx.coroutines.flow.Flow

interface FavoriteDeliveryRepository {

    fun favoriteStateFlow(id: String): Flow<Boolean>

    fun isFavorite(id: String): Boolean

    suspend fun setFavoriteState(id: String, isFav: Boolean)

}