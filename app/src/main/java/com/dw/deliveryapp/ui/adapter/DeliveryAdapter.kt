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
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject


@FragmentScoped
class DeliveryAdapter @Inject constructor(
    private val appResources: Resources,
) :
    PagingDataAdapter<Delivery, DeliveryAdapter.DeliveryViewHolder>(DeliveryComparator()) {

    private var onItemClickListener: ((Delivery) -> Unit)? = null

    fun setOnItemClickListener(listener: (Delivery) -> Unit) {
        onItemClickListener = listener
    }

    inner class DeliveryViewHolder(
        private val binding: ItemDeliveryBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(delivery: Delivery) {
            binding.apply {
                viewDeliveryItem.apply {
                    val shimmerDrawable = ShimmerDrawable().apply {
                        setShimmer(
                            Shimmer.AlphaHighlightBuilder()
                                .setDuration(1500)
                                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                                .setAutoStart(true)
                                .build()
                        )
                    }
                    Glide
                        .with(this)
                        .load(delivery.goodsPicture)
                        .placeholder(shimmerDrawable)
                        .error(shimmerDrawable)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageGoodsPicture)
                    setOnClickListener {
                        onItemClickListener?.let { it(delivery) }
                    }

                    textAmount.text = delivery.page.toString()
                    textFrom.text = appResources.getString(R.string.label_from, delivery.routeStart)
                    textTo.text = appResources.getString(R.string.label_to, delivery.routeEnd)
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