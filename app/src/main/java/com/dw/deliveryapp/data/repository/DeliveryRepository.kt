package com.dw.deliveryapp.data.repository

import androidx.paging.PagingData
import com.dw.deliveryapp.data.model.Delivery
import kotlinx.coroutines.flow.Flow

interface DeliveryRepository {
    fun getDeliveryPage(): Flow<PagingData<Delivery>>
}