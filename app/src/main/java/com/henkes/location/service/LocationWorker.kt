package com.henkes.location.service

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.ForegroundInfo
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.henkes.location.notification.NotificationUtil
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

@HiltWorker
class LocationWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val locationUtil: LocationUtil,
) : CoroutineWorker(appContext, params) {

    override suspend fun getForegroundInfo(): ForegroundInfo = createForegroundInfo()

    override suspend fun doWork(): Result {
        notify(applicationContext, "Worker working on")

        val result = if (locationUtil.emitLocation(LocationCollectionMethod.WORKER)) {
            Result.success()
        } else {
            Result.failure()
        }

        scheduleNext()
        return result
    }

    private fun scheduleNext() {
        enqueue(appContext = applicationContext, initialDelay = 1L)
        Log.i(TAG, "Next work scheduled.")
    }

    private fun createForegroundInfo(): ForegroundInfo {
        val notification = NotificationUtil.buildNotification(
            context = applicationContext,
            title = "WORKER",
            text = "FOREGROUND INFO",
            ongoing = true
        )

        return ForegroundInfo(42, notification)
    }

    companion object {

        private const val TAG = "LOCATIONWORKER"

        fun start(appContext: Context) {
            notify(appContext, "Starting worker")
            enqueue(appContext = appContext, initialDelay = 0L)
        }

        fun stop(appContext: Context) {
            notify(appContext, "Stopping worker")
            WorkManager.getInstance(appContext).cancelAllWorkByTag(TAG)
        }

        private fun enqueue(appContext: Context, initialDelay: Long) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val work = OneTimeWorkRequest.Builder(LocationWorker::class.java)
                .addTag(TAG)
                .setInitialDelay(initialDelay, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance(appContext)
                .beginUniqueWork(TAG, ExistingWorkPolicy.APPEND_OR_REPLACE, work)
                .enqueue()
        }

        private fun notify(context: Context, title: String) {
            NotificationUtil.notify(
                1,
                NotificationUtil.buildNotification(
                    context,
                    title = title
                )
            )
        }

    }

}
