package com.dw.deliveryapp.data.dto

data class DeliveryDto(
    val id: String,
    val remarks: String,
    val pickupTime: String,
    val goodsPicture: String,
    val deliveryFee: String,
    val surcharge: String,
    val route: Route,
    val sender: Sender,
    var offset: Int
) {
    data class Route(
        val start: String,
        val end: String
    )

    data class Sender(
        val phone: String,
        val name: String,
        val email: String
    )
}



