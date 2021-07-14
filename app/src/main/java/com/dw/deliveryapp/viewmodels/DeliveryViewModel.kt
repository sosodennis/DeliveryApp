package com.dw.deliveryapp.viewmodels

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dw.deliveryapp.data.model.Delivery
import com.dw.deliveryapp.data.repository.DeliveryRepository
import com.dw.deliveryapp.data.repository.FavoriteDeliveryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeliveryViewModel @Inject constructor(
    private val deliveryRepository: DeliveryRepository,
    private val favoriteDeliveryRepository: FavoriteDeliveryRepository
) : ViewModel() {
    private val _deliveryPagingDataViewStates =
        deliveryRepository.getDeliveryPage()
            .cachedIn(viewModelScope)
            .asLiveData()
            .let { it as MutableLiveData<PagingData<Delivery>> }

    val deliveryPagingDataStates: LiveData<PagingData<Delivery>> = _deliveryPagingDataViewStates

    fun favouriteState(id: String) =
        favoriteDeliveryRepository.favoriteStateFlow(id).flowOn(Dispatchers.IO)

    fun getDeliveryPage() =
        deliveryRepository.getDeliveryPage().cachedIn(viewModelScope)


    fun toggleFavorite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (favoriteDeliveryRepository.isFavorite(id)) {
                favoriteDeliveryRepository.setFavoriteState(id, false)
            } else {
                favoriteDeliveryRepository.setFavoriteState(id, true)
            }
        }
    }
}