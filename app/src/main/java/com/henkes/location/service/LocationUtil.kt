package com.henkes.location.service

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.henkes.location.data.dao.LocationDao
import com.henkes.location.data.entity.LocationEntity
import com.henkes.location.di.Dispatcher
import com.henkes.location.di.Scope
import com.henkes.location.notification.NotificationUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val TAG = "LOCATION"

class LocationUtil(
    private val locationProvider: FusedLocationProviderClient,
    private val locationDao: LocationDao,
    private val context: Context,
    @Scope private val coroutineScope: CoroutineScope,
    @Dispatcher private val coroutineDispatcher: CoroutineDispatcher,
) {

    fun getLocation(
        collectionMethod: LocationCollectionMethod,
        executeAfter: (() -> Unit)? = null
    ) {
        coroutineScope.launch(coroutineDispatcher) {
            emitLocation(collectionMethod)
            executeAfter?.invoke()
        }
    }

    suspend fun emitLocation(
        collectionMethod: LocationCollectionMethod,
    ): Boolean {
        val location = runBlocking { fetchLocation() }
        return if (location != null) {
            insertLocation(location, collectionMethod)
            val coordinates =
                "Location: [${location.latitude};${location.longitude}] - ${formatTime(location.time)}."
            log(coordinates)
            notify(
                title = "Success",
                text = coordinates
            )
            true
        } else {
            notify(
                title = "Error",
                text = "Location not found - ${formatTime(System.currentTimeMillis())}"
            )
            false
        }
    }

    private suspend fun fetchLocation(): Location? {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO request permission
            log("Need to request the location permissions.")
            return null
        }

        return try {
            log("Fetching the current location.")
            locationProvider.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY, // It won't return using other than high accuracy
                null
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            log("Current location not found.")
            null
        } ?: try {
            log("Fetching the last known location.")
            locationProvider.lastLocation.await()
        } catch (e: Exception) {
            e.printStackTrace()
            log("Last known location not found.")
            null
        }
    }

    private fun formatTime(time: Long): String {
        val dataFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SS", Locale.getDefault())
        return dataFormat.format(Date(time))
    }

    private fun insertLocation(
        location: Location,
        collectionMethod: LocationCollectionMethod,
    ) {
        coroutineScope.launch(coroutineDispatcher) {
            locationDao.insert(
                LocationEntity(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    time = formatTime(location.time),
                    collectionMethod = collectionMethod.toString(),
                    collectedAt = formatTime(System.currentTimeMillis())
                )
            )
        }
    }

    private fun notify(title: String, text: String) {
        NotificationUtil.notify(
            id = 2,
            NotificationUtil.buildNotification(context, title, text)
        )
    }

    private fun log(message: String) {
        Log.i(TAG, message)
    }

}
