package com.dw.deliveryapp.ui.adapter


import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.signature.ObjectKey
import com.dw.deliveryapp.R
import com.dw.deliveryapp.data.model.Delivery
import com.dw.deliveryapp.databinding.ItemDeliveryBinding
import com.dw.deliveryapp.ui.const.TransitionName
import com.dw.deliveryapp.util.DateTimeFormat
import com.dw.deliveryapp.util.DateTimeFormatUtil
import java.util.*
import javax.inject.Inject


class DeliveryAdapter @Inject constructor(private val appResources: Resources) :
    PagingDataAdapter<Delivery, DeliveryAdapter.DeliveryViewHolder>(DeliveryComparator()) {

    private var onItemClickListener: ((Int, Delivery, ItemDeliveryBinding) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int, Delivery, ItemDeliveryBinding) -> Unit) {
        onItemClickListener = listener
    }

    inner class DeliveryViewHolder(
        private val binding: ItemDeliveryBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(delivery: Delivery) {
            binding.apply {
                viewDeliveryItem.apply {
//                    Use Shimmer effect can provide better experience to user?
//                    val shimmerDrawable = ShimmerDrawable().apply {
//                        setShimmer(
//                            Shimmer.AlphaHighlightBuilder()
//                                .setBaseAlpha(0.8f)
//                                .setDuration(1500)
//                                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
//                                .setAutoStart(true)
//                                .build()
//                        )
//                    }
                    imageGoodsPicture.transitionName =
                        TransitionName.IMAGE_GOODS_PICTURE + delivery.id
                    Glide
                        .with(this)
                        .load(delivery.goodsPicture)
                        /*
                         * Use signature to invalidate image cache.
                         * This case simply invalidate each hour.
                         */
                        .signature(
                            ObjectKey(
                                DateTimeFormatUtil.formatStr(
                                    DateTimeFormat.FORMAT_yyyy_MM_dd_HH,
                                    Date()
                                )
                            )
                        )
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.placeholder_error_image)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imageGoodsPicture)

                    delivery.fav?.let {
                        if (it) imageFav.visibility = View.VISIBLE
                        else imageFav.visibility = View.INVISIBLE
                    }

                    textPickupTime.transitionName = TransitionName.TEXT_PICKUP_TIME + delivery.id
                    textPickupTime.text = delivery.convertedPickupTime()

                    textFrom.transitionName = TransitionName.TEXT_FROM + delivery.id
                    textFrom.text = delivery.routeStart

                    textTo.transitionName = TransitionName.TEXT_TO + delivery.id
                    textTo.text = delivery.routeEnd

                    textTotal.transitionName = TransitionName.TEXT_TOTAL + delivery.id
                    textTotal.text = delivery.displayTotalPrice()

                    setOnClickListener {
                        onItemClickListener?.let {
                            it(
                                absoluteAdapterPosition, delivery, binding
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
        val currentItem = getItem(position) ?: return
        holder.bind(currentItem)
    }

    class DeliveryComparator : DiffUtil.ItemCallback<Delivery>() {
        override fun areItemsTheSame(oldItem: Delivery, newItem: Delivery): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Delivery, newItem: Delivery): Boolean {
            /*
             * Currently only fav field will be edited or updated.
             */
            return oldItem.fav == newItem.fav
        }
    }
}