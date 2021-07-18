package com.dw.deliveryapp.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.dw.deliveryapp.R
import com.dw.deliveryapp.databinding.FragmentDeliveryBinding
import com.dw.deliveryapp.ui.adapter.DeliveryAdapter
import com.dw.deliveryapp.ui.adapter.DeliveryLoadStateAdapter
import com.dw.deliveryapp.ui.const.TransitionName
import com.dw.deliveryapp.viewmodels.DeliveryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DeliveryFragment : BaseFragment() {
    @Inject
    lateinit var deliveryAdapter: DeliveryAdapter

    private val viewModel: DeliveryViewModel by activityViewModels()

    private var _binding: FragmentDeliveryBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycleScope.launchWhenCreated {
            observeData()
        }
    }

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
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            setupRecyclerView()
            deliveryAdapter.loadStateFlow.collectLatest { loadStates ->
                if (loadStates.refresh is LoadState.NotLoading
                    || loadStates.refresh is LoadState.Error
                ) {
                    binding.refreshLayoutDeliveries.isRefreshing = false
                } else if (loadStates.refresh is LoadState.Loading) {
                    binding.refreshLayoutDeliveries.isRefreshing = true
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_delivery, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> {
                viewLifecycleOwner.lifecycleScope.launch {
                    if (!binding.refreshLayoutDeliveries.isRefreshing) {
                        deliveryAdapter.refresh()
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() {
        binding.apply {
            postponeEnterTransition()
            recyclerView.apply {
                deliveryAdapter.setOnItemClickListener { delivery, binding ->
                    val action =
                        DeliveryFragmentDirections.actionDeliveryFragmentToDeliveryDetailFragment(
                            delivery
                        )
                    val extra =
                        FragmentNavigatorExtras(
                            binding.imageGoodsPicture to TransitionName.IMAGE_GOODS_PICTURE + delivery.id,
                            binding.textPickupTime to TransitionName.TEXT_PICKUP_TIME + delivery.id,
                            binding.textFrom to TransitionName.TEXT_FROM + delivery.id,
                            binding.textTo to TransitionName.TEXT_TO + delivery.id,
                            binding.textTotal to TransitionName.TEXT_TOTAL + delivery.id
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
            refreshLayoutDeliveries.apply {
                setOnRefreshListener {
                    deliveryAdapter.refresh()
                }
            }
        }
    }

    private suspend fun observeData() {
        viewModel.getDeliveryPage().collectLatest {
            deliveryAdapter.submitData(it)
        }
    }

    private fun cleanUp() {
        binding.recyclerView.adapter = null
    }


}