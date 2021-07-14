package com.dw.deliveryapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.dw.deliveryapp.databinding.FragmentDeliveryBinding
import com.dw.deliveryapp.ui.adapter.DeliveryAdapter
import com.dw.deliveryapp.ui.adapter.DeliveryLoadStateAdapter
import com.dw.deliveryapp.ui.const.TransitionName
import com.dw.deliveryapp.viewmodels.DeliveryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class DeliveryFragment : BaseFragment() {
    @Inject
    lateinit var deliveryAdapter: DeliveryAdapter

    private val viewModel: DeliveryViewModel by activityViewModels()

    private var _binding: FragmentDeliveryBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeliveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cleanUp()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeData()
    }


    private fun setupRecyclerView() {
        binding.apply {
            postponeEnterTransition()
            recyclerView.apply {
                deliveryAdapter.setOnItemClickListener { index, delivery, binding ->
                    val action =
                        DeliveryFragmentDirections.actionDeliveryFragmentToDeliveryDetailFragment(
                            index,
                            delivery
                        )
                    val extra =
                        FragmentNavigatorExtras(
                            binding.imageGoodsPicture to TransitionName.IMAGE_GOODS_PICTURE + delivery.id,
                            binding.textFrom to TransitionName.TEXT_FROM + delivery.id,
                            binding.textTo to TransitionName.TEXT_TO + delivery.id
                        )
                    navigateSafe(action, extra)
                }

                adapter = deliveryAdapter.withLoadStateFooter(
                    footer = DeliveryLoadStateAdapter(deliveryAdapter)
                )

                layoutManager = object : LinearLayoutManager(requireContext()) {
                    override fun canScrollVertically(): Boolean {
                        //TODO: Handle refreshing disable scroll?
                        return true
                    }
                }
                doOnPreDraw {
                    startPostponedEnterTransition()
                }
            }
            refreshLayoutDeliveries.setOnRefreshListener {
                deliveryAdapter.refresh()
                refreshLayoutDeliveries.isRefreshing = false
            }
        }
    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            viewModel.deliveryPagingDataStates.observe(viewLifecycleOwner, { deliveryPagingData ->
                deliveryAdapter.submitData(viewLifecycleOwner.lifecycle, deliveryPagingData)
            })
            viewModel.favoriteStateEvent.collectLatest { event ->
                when (event) {
                    is DeliveryViewModel.FavoriteStateEvent.Updated -> {
                        viewModel.updated(event.id, event.isFav)
                    }
                }
            }
        }
    }

    private fun cleanUp() {
        binding.recyclerView.adapter = null
    }


}