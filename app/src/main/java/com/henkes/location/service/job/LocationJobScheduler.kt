package com.henkes.location.service.job

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.util.Log
import com.henkes.location.notification.NotificationUtil
import kotlin.random.Random

private const val TAG = "JOB"

class LocationJobScheduler(
    private val context: Context
) {

    private val jobScheduler = context.getSystemService(JobScheduler::class.java)

    private var id: Int? = null

    fun schedule() {
        val interval = 1L * 60L * 1000L

        val name = ComponentName(context, LocationJob::class.java)

        val info = JobInfo.Builder(getIdentifier(), name)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setPersisted(true)
            // .setPeriodic(interval) Requested interval +1m0s0ms for job -872215835 is too small; raising to +15m0s0ms
            .setMinimumLatency(interval) //works sometimes - shouldn't be added on devices with API > N
            .build()

        jobScheduler.schedule(info)

        NotificationUtil.notify(
            1,
            NotificationUtil.buildNotification(
                context,
                title = "Job scheduled"
            )
        )

        Log.i(TAG, "Job scheduled.")
    }

    fun cancel() {
        jobScheduler.cancel(getIdentifier())
        NotificationUtil.notify(
            1,
            NotificationUtil.buildNotification(
                context,
                title = "Job canceled"
            )
        )
        Log.i(TAG, "Job canceled.")
    }

    private fun getIdentifier(): Int {
        return id ?: Random.nextInt().apply {
            id = this
        }
    }

}
