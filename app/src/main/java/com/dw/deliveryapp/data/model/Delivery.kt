package com.dw.deliveryapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deliveries")
data class Delivery(
    @PrimaryKey val id: String,
    val remarks: String,
    val pickupTime: String,
    val goodsPicture: String,
    val deliveryFee: String,
    val surcharge: String,
    val routeStart: String,
    val routeEnd: String,
    val senderPhone: String,
    val senderName: String,
    val senderEmail: String,
    val offset: Int
    //TODO: add other fields
) {
    override fun toString() = id
}