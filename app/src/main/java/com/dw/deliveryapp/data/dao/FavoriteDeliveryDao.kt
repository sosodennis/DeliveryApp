package com.dw.deliveryapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dw.deliveryapp.data.model.FavoriteDelivery
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDeliveryDao {
    @Query("SELECT * FROM favorite_deliveries WHERE id = :id")
    fun getFavorDeliveryByIdFlow(id: String): Flow<FavoriteDelivery?>

    @Query("SELECT * FROM favorite_deliveries WHERE id = :id")
    fun getFavorDeliveryById(id: String): FavoriteDelivery?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteDelivery: FavoriteDelivery)

    @Query("DELETE FROM favorite_deliveries WHERE id = :id")
    suspend fun delete(id: String)
}
