package com.henkes.location.service.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.henkes.location.notification.NotificationUtil
import com.henkes.location.service.LocationCollectionMethod
import com.henkes.location.service.LocationUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "ALARM"

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var locationUtil: LocationUtil

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i(TAG, "Alarm received")
        context?.let {
            NotificationUtil.notify(
                1,
                NotificationUtil.buildNotification(
                    it,
                    title = "Alarm on"
                )
            )
        }
        locationUtil.getLocation(LocationCollectionMethod.ALARM)
    }

}
