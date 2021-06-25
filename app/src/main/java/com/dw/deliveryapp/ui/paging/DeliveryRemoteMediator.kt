package com.dw.deliveryapp.ui.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.dw.deliveryapp.api.DeliveryService
import com.dw.deliveryapp.data.dao.DeliveryDao
import com.dw.deliveryapp.data.mapper.DeliveryMapper
import com.dw.deliveryapp.data.model.Delivery


@ExperimentalPagingApi
class DeliveryRemoteMediator(
    private val deliveryDao: DeliveryDao,
    private val deliveryService: DeliveryService,
    private val deliveryMapper: DeliveryMapper
) :
    RemoteMediator<Int, Delivery>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Delivery>
    ): MediatorResult {
        TODO("Not yet implemented")
    }

    private fun getPageKeyData(  loadType: LoadType,
                                 state: PagingState<Int, Delivery>): Any{
        return when(loadType){
            LoadType.REFRESH -> {
            }
            LoadType.PREPEND -> TODO()
            LoadType.APPEND -> TODO()
        }
    }
    
}