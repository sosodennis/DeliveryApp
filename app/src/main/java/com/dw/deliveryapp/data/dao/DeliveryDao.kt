package com.dw.deliveryapp.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dw.deliveryapp.data.model.Delivery
import kotlinx.coroutines.flow.Flow


@Dao
interface DeliveryDao {
    @Query("SELECT * FROM deliveries")
    fun getDeliveries(): PagingSource<Int, Delivery>

    @Query("SELECT * FROM deliveries WHERE id = :id")
    fun getDelivery(id: String): Flow<Delivery>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(deliveries: List<Delivery>)

    @Query("DELETE FROM deliveries")
    suspend fun deleteAll()

    @Query("UPDATE deliveries SET fav = :isFav WHERE id = :id")
    suspend fun setFavorite(id: String, isFav: Boolean)
}