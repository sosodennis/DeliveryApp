package com.dw.deliveryapp.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import androidx.test.filters.SmallTest
import com.dw.deliveryapp.data.dto.DeliveryDto
import com.dw.deliveryapp.data.mapper.DeliveryMapper
import com.dw.deliveryapp.util.getOrAwaitValue
import com.dw.deliveryapp.util.readJsonFromResources
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class DeliveryDaoTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var deliveryDao: DeliveryDao

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
    fun insertDeliveryItems() {
        runBlocking {
            val data = readJsonFromResources(this.javaClass, "delivery_list.json")
            val gson = Gson()
            val deliveryDtoType = object : TypeToken<List<DeliveryDto>>() {}.type
            val deliveryDtoList = gson.fromJson<List<DeliveryDto>>(data, deliveryDtoType)
            val deliveryList = mapper.toEntityList(deliveryDtoList)
            deliveryDao.insertAll(deliveryList)
            val deliveryListFromDb = deliveryDao.getAll().getOrAwaitValue()
            Assert.assertEquals(deliveryList, deliveryListFromDb)
        }
    }

    @Test
    fun deleteAllDeliveryItems() {
        runBlocking {
            val data = readJsonFromResources(this.javaClass, "delivery_list.json")
            val gson = Gson()
            val deliveryDtoType = object : TypeToken<List<DeliveryDto>>() {}.type
            val deliveryDtoList = gson.fromJson<List<DeliveryDto>>(data, deliveryDtoType)
            val deliveryList = mapper.toEntityList(deliveryDtoList)
            deliveryDao.insertAll(deliveryList)
            deliveryDao.deleteAll()
            val deliveryListFromDb = deliveryDao.getAll().getOrAwaitValue()
            assertThat(deliveryListFromDb).doesNotContain(deliveryDtoList)
        }
    }

    @Test
    fun setDeliveryFavoriteTrue() {
        runBlocking {
            val data = readJsonFromResources(this.javaClass, "delivery.json")
            val gson = Gson()
            val deliveryDto = gson.fromJson(data, DeliveryDto::class.java)
            val deliveryList = mapper.toEntityList(listOf(deliveryDto))
            deliveryDao.insertAll(deliveryList)
            deliveryDao.setFavorite(deliveryList.last().id, true)
            val deliveryFromDb = deliveryDao.getDelivery(deliveryList.last().id).getOrAwaitValue()
            Assert.assertTrue(deliveryFromDb.fav)
        }
    }


}