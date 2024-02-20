package com.henkes.location.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.Priority
import com.henkes.location.R

object NotificationUtil {

    private var manager: NotificationManager? = null
    private const val channelId = "location"

    fun createChannel(applicationContext: Context) {
        val channel = NotificationChannel(
            channelId,
            "Location",
            NotificationManager.IMPORTANCE_HIGH
        )

        val notificationManager: NotificationManager? =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager

        notificationManager?.createNotificationChannel(channel)
        manager = notificationManager

    }

    fun buildNotification(
        context: Context,
        title: String,
        text: String? = null,
        ongoing: Boolean = false,
    ): Notification {
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_location_notification_icon)
            .setOngoing(ongoing)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        text?.let { notification.setContentText(it) }

        return notification.build()
    }

    fun notify(id: Int, notification: Notification) {
        manager?.notify(id, notification)
    }

}
