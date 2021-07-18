package com.dw.deliveryapp.ui


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId

import com.dw.deliveryapp.R
import com.dw.deliveryapp.util.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class DeliveryFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun testDeliveryFragmentVisibleOnAppLaunch() {
        launchFragmentInHiltContainer<DeliveryFragment>()
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }

}