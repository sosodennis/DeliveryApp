package com.dw.deliveryapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dw.deliveryapp.data.repository.DeliveryRepository
import com.dw.deliveryapp.data.repository.FavoriteDeliveryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DeliveryViewModel @Inject constructor(
    private val deliveryRepository: DeliveryRepository,
    private val favoriteDeliveryRepository: FavoriteDeliveryRepository
) : ViewModel() {

    fun favouriteState(id: String) =
        favoriteDeliveryRepository.favoriteStateFlow(id).flowOn(Dispatchers.IO)

    suspend fun getDeliveryPage() = withContext(Dispatchers.IO) {
        deliveryRepository.getDeliveryPage().cachedIn(viewModelScope)
    }

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