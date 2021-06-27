package com.dw.deliveryapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_deliveries")
data class FavoriteDelivery(
    @PrimaryKey val id: String
)