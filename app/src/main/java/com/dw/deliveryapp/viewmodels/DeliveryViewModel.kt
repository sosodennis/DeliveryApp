package com.dw.deliveryapp.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dw.deliveryapp.api.DeliveryService
import com.dw.deliveryapp.data.repository.DeliveryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeliveryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val deliveryRepository: DeliveryRepository,
    private val deliveryService: DeliveryService
) : ViewModel() {

    fun getDeliveryPage() = deliveryRepository.getDeliveryPage().cachedIn(viewModelScope)

    suspend fun deleteDeliveryPageCache() = deliveryRepository.deleteDeliveryPageCache()
}