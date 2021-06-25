package com.dw.deliveryapp.api

import com.dw.deliveryapp.data.dto.DeliveryDto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface DeliveryService {
    companion object {
        private const val BASE_URL = "https://mock-api-mobile.dev.lalamove.com"
        fun create(): DeliveryService {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DeliveryService::class.java)
        }
    }

    @GET("/v2/deliveries")
    suspend fun getDeliveries(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): List<DeliveryDto>
}