package com.dw.deliveryapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    protected fun navigateSafe(destination: NavDirections, navExtras: Navigator.Extras? = null) =
        with(findNavController()) {
            currentDestination?.getAction(destination.actionId)
                ?.let {
                    when (navExtras) {
                        null -> navigate(destination)
                        else -> navigate(destination, navExtras)
                    }
                }
        }
}