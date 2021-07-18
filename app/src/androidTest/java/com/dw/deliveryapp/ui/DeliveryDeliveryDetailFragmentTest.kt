package com.dw.deliveryapp.ui

import android.os.Bundle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.dw.deliveryapp.R
import com.dw.deliveryapp.data.dto.DeliveryDto
import com.dw.deliveryapp.data.mapper.DeliveryMapper
import com.dw.deliveryapp.data.model.Delivery
import com.dw.deliveryapp.util.AmountUtils
import com.dw.deliveryapp.util.launchFragmentInHiltContainer
import com.dw.deliveryapp.util.readJsonFromResources
import com.google.gson.Gson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class DeliveryDeliveryDetailFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var mapper: DeliveryMapper

    lateinit var delivery: Delivery

    @Before
    fun setup() {
        hiltRule.inject()
        val data = readJsonFromResources(this.javaClass, "delivery.json")
        val gson = Gson()
        val deliveryDto = gson.fromJson(data, DeliveryDto::class.java)
        val deliveryList = mapper.toEntityList(listOf(deliveryDto))
        delivery = deliveryList.last()
    }


    @ExperimentalCoroutinesApi
    @Test
    fun testDeliveryDetailFragmentWithMockData() {
        val bundle = Bundle()
        bundle.putParcelable("delivery", delivery)
        launchFragmentInHiltContainer<DeliveryDetailFragment>(bundle)
        onView(withId(R.id.text_from)).check(matches(withText(delivery.routeStart)))
        onView(withId(R.id.text_to)).check(matches(withText(delivery.routeEnd)))
        onView(withId(R.id.text_total)).check(matches(withText(AmountUtils.format(delivery.totalPrice))))
    }

}