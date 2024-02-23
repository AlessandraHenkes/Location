package com.henkes.location.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val LOCATION_TABLE = "location_table"

@Entity(LOCATION_TABLE)
data class LocationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo("latitude") val latitude: Double,
    @ColumnInfo("longitude") val longitude: Double,
    @ColumnInfo("time") val time: String,
    @ColumnInfo("collection_method") val collectionMethod: String,
    @ColumnInfo("started_at") val startedAt: String,
    @ColumnInfo("collected_at") val collectedAt: String,
)
