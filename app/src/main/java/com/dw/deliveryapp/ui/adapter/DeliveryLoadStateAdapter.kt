package com.dw.deliveryapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dw.deliveryapp.databinding.ItemDeliveryLoadStateBinding
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
class DeliveryLoadStateAdapter(
    private val adapter: DeliveryAdapter
) : LoadStateAdapter<DeliveryLoadStateAdapter.DeliveryLoadStateItemViewHolder>() {

    inner class DeliveryLoadStateItemViewHolder(
        private val binding: ItemDeliveryLoadStateBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonRetry.setOnClickListener { retryCallback() }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                progressBar.isVisible = loadState is LoadState.Loading
                buttonRetry.isVisible = loadState is LoadState.Error
                textErrorMsg.isVisible =
                    !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                textErrorMsg.text = (loadState as? LoadState.Error)?.error?.message
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState)
            : DeliveryLoadStateItemViewHolder {
        val binding =
            ItemDeliveryLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeliveryLoadStateItemViewHolder(binding) { adapter.retry() }
    }


    override fun onBindViewHolder(holder: DeliveryLoadStateItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }


}