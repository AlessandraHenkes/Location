package com.henkes.location.service.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.henkes.location.notification.NotificationUtil
import kotlin.random.Random

private const val TAG = "ALARM"

class LocationAlarmScheduler(
    private val context: Context,
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    private var id: Int? = null

    override fun schedule() {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("START", true)
        }

        val interval: Long = 1 * 60 * 1000 // 1 minute

        alarmManager.setRepeating(
            AlarmManager.RTC, // can be changed to RTC_WAKEUP to wake the device
            System.currentTimeMillis(),
            interval,
            PendingIntent.getBroadcast(
                context,
                getIdentifier(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )

        NotificationUtil.notify(
            1,
            NotificationUtil.buildNotification(
                context,
                title = "Alarm scheduled"
            )
        )

        Log.i(TAG, "Alarm scheduled.")
    }

    override fun cancel() {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                getIdentifier(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
        NotificationUtil.notify(
            1,
            NotificationUtil.buildNotification(
                context,
                title = "Alarm canceled"
            )
        )
        Log.i(TAG, "Alarm canceled.")
    }

    private fun getIdentifier(): Int {
        return id ?: Random.nextInt().apply {
            id = this
        }
    }

}

interface AlarmScheduler {
    fun schedule()
    fun cancel()
}
