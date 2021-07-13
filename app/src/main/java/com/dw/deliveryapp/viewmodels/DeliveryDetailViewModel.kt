package com.dw.deliveryapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dw.deliveryapp.data.repository.FavoriteDeliveryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeliveryDetailViewModel @Inject constructor(
    private val favoriteDeliveryRepository: FavoriteDeliveryRepository
) : ViewModel() {

    fun isFavouriteFlow(id: String) =
        favoriteDeliveryRepository.isFavoriteFlow(id).flowOn(Dispatchers.IO)


    fun favorite(id: String) {
        viewModelScope.launch { favoriteDeliveryRepository.favorite(id) }
    }

    fun unfavorite(id: String) {
        viewModelScope.launch { favoriteDeliveryRepository.unfavorite(id) }
    }
}