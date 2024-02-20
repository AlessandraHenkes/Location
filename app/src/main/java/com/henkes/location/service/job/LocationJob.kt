package com.henkes.location.service.job

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import androidx.work.Configuration
import com.henkes.location.notification.NotificationUtil
import com.henkes.location.service.LocationCollectionMethod
import com.henkes.location.service.LocationUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "JOB"

@AndroidEntryPoint
class LocationJob : JobService() {

    @Inject
    lateinit var locationUtil: LocationUtil

    override fun onStartJob(params: JobParameters?): Boolean {
        NotificationUtil.notify(
            1,
            NotificationUtil.buildNotification(applicationContext, "Job started")
        )
        Log.i(TAG, "Job started")
        locationUtil.getLocation(LocationCollectionMethod.JOB) {
            jobFinished(params, true)
        }
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.i(TAG, "Stop job")
        return true
    }

    init {
        // need minimum 1000 job ids
        Configuration.Builder().setJobSchedulerJobIdRange(0, 1000).build()
    }

}
