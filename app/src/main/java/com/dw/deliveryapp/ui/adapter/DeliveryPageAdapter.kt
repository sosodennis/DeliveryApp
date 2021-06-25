package com.dw.deliveryapp.ui.adapter


import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dw.deliveryapp.R
import com.dw.deliveryapp.data.model.Delivery
import com.dw.deliveryapp.databinding.ItemDeliveryBinding
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


@FragmentScoped
class DeliveryPageAdapter @Inject constructor(private val appResources: Resources) :
    PagingDataAdapter<Delivery, DeliveryPageAdapter.DeliveryViewHolder>(DeliveryComparator()) {

    class DeliveryViewHolder(
        private val binding: ItemDeliveryBinding,
        private val appResources: Resources
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(delivery: Delivery) {
            binding.apply {
                textAmount.text = delivery.offset.toString()
                textFrom.text = appResources.getString(R.string.label_from, delivery.routeStart)
                textTo.text = appResources.getString(R.string.label_to, delivery.routeEnd)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val binding =
            ItemDeliveryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeliveryViewHolder(binding, appResources)
    }

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class DeliveryComparator : DiffUtil.ItemCallback<Delivery>() {
        override fun areItemsTheSame(oldItem: Delivery, newItem: Delivery): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Delivery, newItem: Delivery): Boolean {
            return oldItem == newItem
        }
    }
}