package com.dw.deliveryapp.ui

import android.content.res.Resources
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dw.deliveryapp.R
import com.dw.deliveryapp.databinding.FragmentDeliveryDetailBinding
import com.dw.deliveryapp.ui.const.TransitionName
import com.dw.deliveryapp.viewmodels.DeliveryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class DeliveryDetailFragment : BaseFragment() {
    private var _binding: FragmentDeliveryDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DeliveryViewModel by activityViewModels()

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_delivery_detail, menu)
        //TODO: Find item and set the latest favorite value
        val item = menu.findItem(R.id.action_toggle_favorite)
        lifecycleScope.launch {
            viewModel.favouriteState(args.delivery.id).collectLatest { isFav ->
                if (isFav) {
                    item?.setIcon(R.drawable.ic_favorite_24)
                } else {
                    item?.setIcon(R.drawable.ic_favorite_border_24)
                }
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_toggle_favorite -> {
                viewModel.toggleFavorite(args.delivery.id)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}