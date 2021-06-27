package com.dw.deliveryapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "delivery_remote_keys")
data class DeliveryRemoteKey(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)
