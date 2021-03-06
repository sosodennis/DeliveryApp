package com.dw.deliveryapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.dw.deliveryapp.data.db.AppDatabase
import com.dw.deliveryapp.ui.paging.DeliveryRemoteMediator
import javax.inject.Inject

private const val PAGE_SIZE = 20
private const val PREFETCH_DISTANCE = 10


class DeliveryRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val deliveryRemoteMediator: DeliveryRemoteMediator
) : DeliveryRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getDeliveryPage() = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            prefetchDistance = PREFETCH_DISTANCE,
            enablePlaceholders = true
        ),
        remoteMediator = deliveryRemoteMediator,
        pagingSourceFactory = { appDatabase.deliveryDao().getDeliveries() }).flow
}