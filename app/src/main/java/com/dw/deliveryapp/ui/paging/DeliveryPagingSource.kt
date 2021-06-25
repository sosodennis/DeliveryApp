package com.dw.deliveryapp.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dw.deliveryapp.api.DeliveryService
import com.dw.deliveryapp.data.mapper.DeliveryMapper
import com.dw.deliveryapp.data.model.Delivery
import java.lang.Exception


class DeliveryPagingSource constructor(
    private val deliveryService: DeliveryService,
    private val deliveryMapper: DeliveryMapper
) :
    PagingSource<Int, Delivery>() {
    override fun getRefreshKey(state: PagingState<Int, Delivery>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Delivery> {
        return try {
            val position = params.key ?: 1
            val limit = 10
            val offset = (position - 1) * limit

            val deliveriesDto = deliveryService.getDeliveries(offset, limit)
            var index = offset
            deliveriesDto.map { it.offset = index++ }
            val deliveries =
                deliveryMapper.toEntityList(deliveriesDto)

            LoadResult.Page(
                data = deliveries,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (deliveries.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}