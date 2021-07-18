package com.dw.deliveryapp.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import androidx.test.filters.SmallTest
import com.dw.deliveryapp.data.dto.DeliveryDto
import com.dw.deliveryapp.data.mapper.DeliveryMapper
import com.dw.deliveryapp.data.model.FavoriteDelivery
import com.dw.deliveryapp.util.readJsonFromResources
import com.google.gson.Gson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.*
import javax.inject.Inject

@SmallTest
@HiltAndroidTest
class FavoriteDeliveryDaoTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var favoriteDeliveryDao: FavoriteDeliveryDao

    @Inject
    lateinit var mapper: DeliveryMapper

    @ExperimentalPagingApi
    @Before
    fun setup() {
        hiltRule.inject()
    }

    @After
    fun teardown() {

    }

    @Test
    fun insertFavoriteDelivery() {
        runBlocking {
            val data = readJsonFromResources(this.javaClass, "delivery.json")
            val gson = Gson()
            val deliveryDto = gson.fromJson(data, DeliveryDto::class.java)
            val deliveryList = mapper.toEntityList(listOf(deliveryDto))
            val delivery = deliveryList.last()
            favoriteDeliveryDao.insert(FavoriteDelivery(delivery.id))
            val isIdExist = favoriteDeliveryDao.isIdExist(delivery.id)
            Assert.assertTrue(isIdExist)
        }
    }

    @Test
    fun deleteFavoriteDelivery() {
        runBlocking {
            val data = readJsonFromResources(this.javaClass, "delivery.json")
            val gson = Gson()
            val deliveryDto = gson.fromJson(data, DeliveryDto::class.java)
            val deliveryList = mapper.toEntityList(listOf(deliveryDto))
            val delivery = deliveryList.last()
            favoriteDeliveryDao.insert(FavoriteDelivery(delivery.id))
            favoriteDeliveryDao.delete(delivery.id)
            val isIdExist = favoriteDeliveryDao.isIdExist(delivery.id)
            Assert.assertFalse(isIdExist)
        }
    }
}