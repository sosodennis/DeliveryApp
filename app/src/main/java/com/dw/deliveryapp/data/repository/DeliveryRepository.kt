package com.dw.deliveryapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.dw.deliveryapp.api.DeliveryService
import com.dw.deliveryapp.data.db.AppDatabase
import com.dw.deliveryapp.data.mapper.DeliveryMapper
import com.dw.deliveryapp.ui.paging.DeliveryRemoteMediator
import javax.inject.Inject

private const val PAGE_SIZE = 20
private const val PREFETCH_DISTANCE = 10


class DeliveryRepository @Inject constructor(
    private val appDatabase: AppDatabase,
    private val deliveryService: DeliveryService,
    private val deliveryMapper: DeliveryMapper
) {
    @OptIn(ExperimentalPagingApi::class)
    fun getDeliveryPage() = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            prefetchDistance = PREFETCH_DISTANCE,
            enablePlaceholders = true
        ),
        remoteMediator = DeliveryRemoteMediator(appDatabase, deliveryService, deliveryMapper),
        pagingSourceFactory = { appDatabase.deliveryDao().getDeliveries() }).flow
}