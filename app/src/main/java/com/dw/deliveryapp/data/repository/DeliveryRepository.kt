package com.dw.deliveryapp.data.repository

import com.dw.deliveryapp.data.dao.DeliveryDao
import com.dw.deliveryapp.data.model.Delivery
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeliveryRepository @Inject constructor(private val deliveryDao: DeliveryDao){
    //TODO: DeliveryRepository
    fun getDeliveries() = deliveryDao.getDeliveries()

    suspend fun addDelivery(delivery: Delivery) = deliveryDao.insertAll(listOf(delivery))
}