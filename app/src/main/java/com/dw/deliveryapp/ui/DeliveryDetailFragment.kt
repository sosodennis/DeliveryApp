package com.dw.deliveryapp.ui

import android.content.res.Resources
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dw.deliveryapp.R
import com.dw.deliveryapp.databinding.FragmentDeliveryDetailBinding
import com.dw.deliveryapp.ui.const.TransitionName
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DeliveryDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class DeliveryDetailFragment : BaseFragment() {
    private var _binding: FragmentDeliveryDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DeliveryDetailFragmentArgs>()

    @Inject
    lateinit var appResources: Resources

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeliveryDetailBinding.inflate(inflater, container, false)
        val transition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
        postponeEnterTransition(250, TimeUnit.MILLISECONDS)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            imageGoodsPicture.transitionName = TransitionName.IMAGE_GOODS_PICTURE + args.delivery.id
            viewDeliveryDetail.apply {
                Glide
                    .with(this)
                    .load(args.delivery.goodsPicture)
                    .placeholder(R.drawable.placeholder_image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageGoodsPicture)
            }

            textFrom.transitionName = TransitionName.TEXT_FROM + args.delivery.id
            textFrom.text = appResources.getString(R.string.label_from, args.delivery.routeStart)

            textTo.transitionName = TransitionName.TEXT_TO + args.delivery.id
            textTo.text = appResources.getString(R.string.label_to, args.delivery.routeEnd)

            textRemarks.text = args.delivery.remarks

        }
    }
}