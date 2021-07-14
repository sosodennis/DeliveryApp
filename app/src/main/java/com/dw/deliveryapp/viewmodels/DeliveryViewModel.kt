package com.dw.deliveryapp.viewmodels

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.dw.deliveryapp.data.model.Delivery
import com.dw.deliveryapp.data.repository.DeliveryRepository
import com.dw.deliveryapp.data.repository.FavoriteDeliveryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeliveryViewModel @Inject constructor(
    private val deliveryRepository: DeliveryRepository,
    private val favoriteDeliveryRepository: FavoriteDeliveryRepository
) : ViewModel() {
    private val favoriteStateEventChannel = Channel<FavoriteStateEvent>()
    val favoriteStateEvent = favoriteStateEventChannel.receiveAsFlow()

    sealed class FavoriteStateEvent {
        data class Updated(val id: String, val isFav: Boolean) :
            FavoriteStateEvent()
    }

    private val _deliveryPagingDataViewStates =
        deliveryRepository.getDeliveryPage()
            .cachedIn(viewModelScope)
            .asLiveData()
            .let { it as MutableLiveData<PagingData<Delivery>> }

    val deliveryPagingDataStates: LiveData<PagingData<Delivery>> = _deliveryPagingDataViewStates

    fun favouriteState(id: String) =
        favoriteDeliveryRepository.favoriteStateFlow(id).flowOn(Dispatchers.IO)


    fun toggleFavorite(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (favoriteDeliveryRepository.isFavorite(id)) {
                favoriteDeliveryRepository.setFavoriteState(id, false)
                favoriteStateEventChannel.send(FavoriteStateEvent.Updated(id, false))
            } else {
                favoriteDeliveryRepository.setFavoriteState(id, true)
                favoriteStateEventChannel.send(FavoriteStateEvent.Updated(id, true))
            }

        }

    }

    fun updated(id: String, isFav: Boolean) {
        viewModelScope.launch {
            deliveryPagingDataStates.value?.let { pagingData ->
                pagingData.map {
                    if (id == it.id) {
                        it.fav = isFav
                        it
                    } else return@map it
                }.let { _deliveryPagingDataViewStates.value = it }
            }
        }
    }
}