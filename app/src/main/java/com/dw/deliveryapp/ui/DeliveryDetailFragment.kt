package com.dw.deliveryapp.ui

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dw.deliveryapp.databinding.FragmentDeliveryDetailBinding
import java.util.concurrent.TimeUnit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DeliveryDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DeliveryDetailFragment : Fragment() {
    private var _binding: FragmentDeliveryDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DeliveryDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeliveryDetailBinding.inflate(inflater, container, false)
        val transition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
        postponeEnterTransition(100, TimeUnit.MILLISECONDS)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            textRemarks.text = args.delivery.remarks
            viewDeliveryDetail.apply {
                Glide
                    .with(this)
                    .load(args.delivery.goodsPicture)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageGoodsPicture)
            }

        }
    }
}