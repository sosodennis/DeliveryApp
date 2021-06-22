package com.dw.deliveryapp.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface DeliveryService {
    companion object {
        private const val BASE_URL = "API_URL"
        fun create(): DeliveryService {
            val client = OkHttpClient.Builder()
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .build()
                .create(DeliveryService::class.java)
        }
    }
}