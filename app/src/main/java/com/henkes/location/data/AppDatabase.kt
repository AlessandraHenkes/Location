package com.henkes.location.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.henkes.location.data.dao.LocationDao
import com.henkes.location.data.entity.LocationEntity

private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "location_database"

@Database(
    entities = [LocationEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    class Builder(private val app: Context) {
        private val builder: RoomDatabase.Builder<AppDatabase>
            get() = Room.databaseBuilder(
                app,
                AppDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration()

        fun build(): AppDatabase = builder.build()
    }

    abstract fun getLocationDao(): LocationDao

}
