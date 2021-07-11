package com.dw.deliveryapp.ui.adapter


import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dw.deliveryapp.R
import com.dw.deliveryapp.data.model.Delivery
import com.dw.deliveryapp.databinding.ItemDeliveryBinding
import com.dw.deliveryapp.ui.const.TransitionName
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


@FragmentScoped
class DeliveryAdapter @Inject constructor(private val appResources: Resources) :
    PagingDataAdapter<Delivery, DeliveryAdapter.DeliveryViewHolder>(DeliveryComparator()) {

    private var onItemClickListener: ((Delivery, ItemDeliveryBinding) -> Unit)? = null

    fun setOnItemClickListener(listener: (Delivery, ItemDeliveryBinding) -> Unit) {
        onItemClickListener = listener
    }

    inner class DeliveryViewHolder(
        private val binding: ItemDeliveryBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(delivery: Delivery) {
            binding.apply {
                viewDeliveryItem.apply {
                    imageGoodsPicture.transitionName =
                        TransitionName.IMAGE_GOODS_PICTURE + delivery.id
                    Glide
                        .with(this)
                        .load(delivery.goodsPicture)
                        .placeholder(R.drawable.placeholder_image)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageGoodsPicture)

                    textAmount.text = delivery.deliveryFee

                    textFrom.transitionName = TransitionName.TEXT_FROM + delivery.id
                    textFrom.text = appResources.getString(R.string.label_from, delivery.routeStart)

                    textTo.transitionName = TransitionName.TEXT_TO + delivery.id
                    textTo.text = appResources.getString(R.string.label_to, delivery.routeEnd)

                    setOnClickListener {
                        onItemClickListener?.let {
                            it(
                                delivery, binding
                            )
                        }
                    }
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val binding =
            ItemDeliveryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeliveryViewHolder(binding)
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