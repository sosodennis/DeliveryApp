package com.dw.deliveryapp.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.dw.deliveryapp.api.DeliveryService
import com.dw.deliveryapp.data.model.Delivery
import com.dw.deliveryapp.data.repository.DeliveryRepository
import com.dw.deliveryapp.ui.paging.DeliveryPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class DeliveryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val deliveryRepository: DeliveryRepository,
    private val deliveryService: DeliveryService
) : ViewModel() {

    val delivery = liveData {
        //emitSource(deliveryRepository.getDeliveries().asLiveData())
        emit(deliveryRepository.getDeliveriesFromNetwork(0, 10))
    }


    fun insert(delivery: Delivery) = viewModelScope.launch(Dispatchers.IO) {
        deliveryRepository.addDelivery(delivery)
    }

    fun getDeliveryPage()= deliveryRepository.getDeliveryPage().flow.cachedIn(viewModelScope)

}