package com.dw.deliveryapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.dw.deliveryapp.data.db.AppDatabase
import com.dw.deliveryapp.ui.paging.DeliveryRemoteMediator
import javax.inject.Inject
import javax.inject.Named

class FakeDeliveryRepositoryImpl @Inject constructor(
    @Named("test_db") private val appDatabase: AppDatabase,
    @Named("test_remote_mediator") private val deliveryRemoteMediator: DeliveryRemoteMediator
) : DeliveryRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getDeliveryPage() = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            prefetchDistance = 10,
            enablePlaceholders = true
        ),
        remoteMediator = deliveryRemoteMediator,
        pagingSourceFactory = { appDatabase.deliveryDao().getDeliveries() }).flow
}