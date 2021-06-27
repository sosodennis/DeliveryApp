package com.dw.deliveryapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dw.deliveryapp.data.model.DeliveryRemoteKey

@Dao
interface DeliveryRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<DeliveryRemoteKey>)

    @Query("SELECT * FROM delivery_remote_keys WHERE id = :id")
    suspend fun getDeliveryRemoteKeyById(id: String): DeliveryRemoteKey

    @Query("DELETE FROM delivery_remote_keys")
    suspend fun deleteAll()
}