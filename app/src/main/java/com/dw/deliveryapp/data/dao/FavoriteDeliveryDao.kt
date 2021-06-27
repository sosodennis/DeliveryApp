package com.dw.deliveryapp.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.dw.deliveryapp.data.model.FavoriteDelivery

@Dao
interface FavoriteDeliveryDao {
    @Query("SELECT * FROM favorite_deliveries WHERE id = :id")
    fun getFavorDeliveryById(id: String): FavoriteDelivery
}
