package com.dw.deliveryapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.dw.deliveryapp.data.repository.DeliveryRepository
import com.dw.deliveryapp.data.repository.FavoriteDeliveryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DeliveryViewModel @Inject constructor(
    private val deliveryRepository: DeliveryRepository,
    private val favoriteDeliveryRepository: FavoriteDeliveryRepository
) : ViewModel() {

    suspend fun getDeliveryPage() = withContext(Dispatchers.IO) {
        deliveryRepository.getDeliveryPage().map { pagingData ->
            pagingData.map {
                //FIXME handle access db in main thread issue
                //it.fav = favoriteDeliveryRepository.isFavorite(it.id) != null
                it.fav = true
                it
            }
        }.cachedIn(viewModelScope)
    }.flowOn(Dispatchers.IO)

}