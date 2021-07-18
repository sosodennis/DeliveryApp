package com.dw.deliveryapp.ui.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.*
import androidx.test.filters.SmallTest
import com.dw.deliveryapp.data.db.AppDatabase
import com.dw.deliveryapp.data.mapper.DeliveryMapper
import com.dw.deliveryapp.data.model.Delivery
import com.dw.deliveryapp.di.TestNetworkModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class DeliveryRemoteMediatorTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var appDatabase: AppDatabase

    @Inject
    lateinit var mapper: DeliveryMapper

    @Inject
    @Named("test_delivery_service")
    lateinit var deliveryService: TestNetworkModule.FakeDeliveryService

    lateinit var deliveryRemoteMediator: DeliveryRemoteMediator

    private lateinit var pagingState: PagingState<Int, Delivery>

    @Before
    fun setup() {
        hiltRule.inject()
        pagingState = PagingState(
            listOf(),
            null,
            PagingConfig(
                pageSize = 20,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                prefetchDistance = 10,
                enablePlaceholders = true
            ),
            10
        )
        deliveryRemoteMediator = DeliveryRemoteMediator(appDatabase, deliveryService, mapper)
    }

    @After
    fun teardown() {

    }

    @ExperimentalPagingApi
    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runBlocking {
        deliveryService.setMockResult(TestNetworkModule.FakeDeliveryService.MockResult.MORE_DATA)
        val result = deliveryRemoteMediator.load(LoadType.REFRESH, pagingState)
        Assert.assertTrue(result is RemoteMediator.MediatorResult.Success)
        Assert.assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @ExperimentalPagingApi
    @Test
    fun refreshLoadSuccessAndEndOfPaginationWhenNoMoreData() = runBlocking {
        deliveryService.setMockResult(TestNetworkModule.FakeDeliveryService.MockResult.NO_MORE_DATA)
        val result = deliveryRemoteMediator.load(LoadType.REFRESH, pagingState)
        Assert.assertTrue(result is RemoteMediator.MediatorResult.Success)
        Assert.assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @ExperimentalPagingApi
    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() = runBlocking {
        deliveryService.setMockResult(TestNetworkModule.FakeDeliveryService.MockResult.ERROR)
        val result = deliveryRemoteMediator.load(LoadType.REFRESH, pagingState)
        Assert.assertTrue(result is RemoteMediator.MediatorResult.Error)
    }
}