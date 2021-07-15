package com.dw.deliveryapp.ui.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.dw.deliveryapp.api.DeliveryService
import com.dw.deliveryapp.data.db.AppDatabase
import com.dw.deliveryapp.data.mapper.DeliveryMapper
import com.dw.deliveryapp.data.model.Delivery
import com.dw.deliveryapp.data.model.DeliveryRemoteKey
import com.dw.deliveryapp.util.AmountUtils
import retrofit2.HttpException
import java.io.IOException

const val STARTING_PAGE_INDEX = 1


@OptIn(ExperimentalPagingApi::class)
class DeliveryRemoteMediator(
    private val appDatabase: AppDatabase,
    private val deliveryService: DeliveryService,
    private val deliveryMapper: DeliveryMapper
) :
    RemoteMediator<Int, Delivery>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Delivery>
    ): MediatorResult {
        val pageKeyData = getPageKeyData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }
        try {
            val offset = (page - 1) * state.config.pageSize
            val deliveriesDto = deliveryService.getDeliveries(offset, state.config.pageSize)
            val deliveries = deliveryMapper.toEntityList(deliveriesDto)
            val isEndOfList = deliveries.isEmpty()
            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    appDatabase.deliveryDao().deleteAll()
                    appDatabase.deliveryRemoteKeyDao().deleteAll()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1

                val keys = deliveries.map {
                    it.fav = appDatabase.favoriteDeliveryDao().isIdExist(it.id)
                    it.displayPrice =
                        AmountUtils.format(it.deliveryFeeInDouble() + it.surchargeInDouble())
                    DeliveryRemoteKey(it.id, prevKey = prevKey, nextKey = nextKey)
                }
                appDatabase.deliveryRemoteKeyDao().insertAll(keys)
                appDatabase.deliveryDao().insertAll(deliveries)
            }
            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getPageKeyData(
        loadType: LoadType,
        state: PagingState<Int, Delivery>
    ): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                STARTING_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevKey
                return prevKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, Delivery>)
            : DeliveryRemoteKey? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { delivery ->
                appDatabase.deliveryRemoteKeyDao().getDeliveryRemoteKeyById(delivery.id)
            }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, Delivery>)
            : DeliveryRemoteKey? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { delivery ->
                appDatabase.deliveryRemoteKeyDao().getDeliveryRemoteKeyById(delivery.id)
            }
    }

}