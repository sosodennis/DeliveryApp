package com.dw.deliveryapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dw.deliveryapp.databinding.FragmentDeliveryBinding
import com.dw.deliveryapp.ui.adapter.DeliveryAdapter
import com.dw.deliveryapp.ui.adapter.DeliveryLoadStateAdapter
import com.dw.deliveryapp.viewmodels.DeliveryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DeliveryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class DeliveryFragment : Fragment() {
    @Inject
    lateinit var deliveryAdapter: DeliveryAdapter

    private val viewModel: DeliveryViewModel by viewModels()

    private var _binding: FragmentDeliveryBinding? = null
    private val binding get() = _binding!!


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDeliveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        observeData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.apply {
            recyclerView.apply {
                deliveryAdapter.setOnItemClickListener {
                    println(it.id)
                    findNavController().navigate(DeliveryFragmentDirections.actionDeliveryFragmentToDeliveryDetailFragment())
                }
                adapter = deliveryAdapter.withLoadStateFooter(
                    footer = DeliveryLoadStateAdapter(deliveryAdapter)
                )
                layoutManager = LinearLayoutManager(this@DeliveryFragment.context)
            }
            refreshLayoutDeliveries.setOnRefreshListener {
                deliveryAdapter.refresh()
                refreshLayoutDeliveries.isRefreshing = false
            }
        }
    }

    private fun observeData() {
        lifecycleScope.launchWhenCreated {
            viewModel.getDeliveryPage().collectLatest {
                deliveryAdapter.submitData(it)
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DeliveryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DeliveryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}