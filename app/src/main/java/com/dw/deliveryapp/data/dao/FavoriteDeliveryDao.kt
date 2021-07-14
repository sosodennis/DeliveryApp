package com.dw.deliveryapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dw.deliveryapp.data.model.FavoriteDelivery
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDeliveryDao {
    @Query("SELECT EXISTS(SELECT * FROM favorite_deliveries WHERE id = :id)")
    fun getFavorStateByIdFlow(id: String): Flow<Boolean>

    @Query("SELECT EXISTS(SELECT * FROM favorite_deliveries WHERE id = :id)")
    fun isIdExist(id: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteDelivery: FavoriteDelivery)

    @Query("DELETE FROM favorite_deliveries WHERE id = :id")
    suspend fun delete(id: String)
}
