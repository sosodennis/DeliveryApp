package com.dw.deliveryapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.dw.deliveryapp.api.DeliveryService
import com.dw.deliveryapp.data.dao.DeliveryDao
import com.dw.deliveryapp.data.mapper.DeliveryMapper
import com.dw.deliveryapp.data.model.Delivery
import com.dw.deliveryapp.ui.paging.DeliveryPagingSource
import com.dw.deliveryapp.ui.paging.DeliveryRemoteMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeliveryRepository @Inject constructor(
    private val deliveryDao: DeliveryDao,
    private val deliveryService: DeliveryService,
    private val deliveryMapper: DeliveryMapper
) {
    fun getDeliveries() = deliveryDao.getDeliveries()

    suspend fun getDeliveriesFromNetwork(offset: Int, limit: Int): List<Delivery> =
        withContext(Dispatchers.IO) {
            val deliveriesDto = deliveryService.getDeliveries(offset, limit)
            var index = 0
            deliveriesDto.map { it.offset = index++ }
            val deliveries =
                deliveryMapper.toEntityList(deliveriesDto)
            deliveryDao.insertAll(deliveries)
            return@withContext (deliveries)
        }

    @ExperimentalPagingApi
    fun getDeliveryPage() = Pager(config = PagingConfig(
        pageSize = 10,
        maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
        enablePlaceholders = false
    ), remoteMediator = DeliveryRemoteMediator(deliveryDao, deliveryService, deliveryMapper)
        , pagingSourceFactory = { DeliveryPagingSource(deliveryService, deliveryMapper) })


    suspend fun addDelivery(delivery: Delivery) = deliveryDao.insertAll(listOf(delivery))
}