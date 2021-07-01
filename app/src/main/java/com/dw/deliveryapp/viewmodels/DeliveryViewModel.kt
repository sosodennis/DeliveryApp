package com.dw.deliveryapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dw.deliveryapp.data.repository.DeliveryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DeliveryViewModel @Inject constructor(
    private val deliveryRepository: DeliveryRepository,
) : ViewModel() {

    suspend fun getDeliveryPage() = withContext(Dispatchers.IO) {
        deliveryRepository.getDeliveryPage().cachedIn(viewModelScope)
    }

}