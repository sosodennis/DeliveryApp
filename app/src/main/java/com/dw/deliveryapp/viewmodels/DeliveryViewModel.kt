package com.dw.deliveryapp.viewmodels

import androidx.lifecycle.*
import com.dw.deliveryapp.api.DeliveryService
import com.dw.deliveryapp.data.model.Delivery
import com.dw.deliveryapp.data.repository.DeliveryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DeliveryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val deliveryRepository: DeliveryRepository,
    private val deliveryService: DeliveryService
) : ViewModel() {
    private var currentDelivery: LiveData<List<Delivery>>? = null


    val delivery = liveData {
        emitSource(deliveryRepository.getDeliveries().asLiveData())
        emit(deliveryService.getDeliveries(1, 100))
    }


    fun insert(delivery: Delivery) = viewModelScope.launch {
        deliveryRepository.addDelivery(delivery)
    }


}