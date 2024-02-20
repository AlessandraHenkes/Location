package com.henkes.location.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.henkes.location.data.entity.LOCATION_TABLE
import com.henkes.location.data.entity.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: LocationEntity)

    @Query("SELECT * FROM $LOCATION_TABLE")
    fun getAll(): Flow<List<LocationEntity>>

    @Query("DELETE FROM $LOCATION_TABLE")
    suspend fun deleteAll()

}
