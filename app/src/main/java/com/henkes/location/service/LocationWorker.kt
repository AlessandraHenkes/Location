package com.henkes.location.service

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.ForegroundInfo
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
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
        val isPeriodic = tags.contains(TAG_PERIODIC)
        val delay = if (isPeriodic) 0L else 1L
        enqueue(
            appContext = applicationContext,
            initialDelay = delay,
            isPeriodic = isPeriodic
        )
        Log.i(TAG, "Next work scheduled. $tags")
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
        private const val TAG_PERIODIC = "LOCATIONWORKERPERIODIC"

        fun start(appContext: Context, isPeriodic: Boolean = false) {
            notify(appContext, "Starting worker")
            enqueue(
                appContext = appContext,
                initialDelay = 0L,
                isPeriodic = isPeriodic
            )
        }

        fun stop(appContext: Context, isPeriodic: Boolean = false) {
            notify(appContext, "Stopping worker")
            val tag = if (isPeriodic) TAG_PERIODIC else TAG
            WorkManager.getInstance(appContext).cancelAllWorkByTag(tag)
        }

        private fun enqueue(appContext: Context, initialDelay: Long, isPeriodic: Boolean) {
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val workManager = WorkManager.getInstance(appContext)

            if (isPeriodic) {
                enqueuePeriodic(constraints, workManager)
            } else {
                enqueueOneTime(constraints, workManager, initialDelay)
            }
        }

        private fun enqueueOneTime(
            constraints: Constraints,
            workManager: WorkManager,
            initialDelay: Long
        ) {
            val work = OneTimeWorkRequest.Builder(LocationWorker::class.java)
                .addTag(TAG)
                .setInitialDelay(initialDelay, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

            workManager
                .beginUniqueWork(TAG, ExistingWorkPolicy.APPEND_OR_REPLACE, work)
                .enqueue()
        }

        private fun enqueuePeriodic(constraints: Constraints, workManager: WorkManager) {
            val work = PeriodicWorkRequestBuilder<LocationWorker>(15, TimeUnit.MINUTES)
                .addTag(TAG_PERIODIC)
                .setConstraints(constraints)
                .build()

            workManager.enqueueUniquePeriodicWork(
                TAG_PERIODIC,
                ExistingPeriodicWorkPolicy.UPDATE,
                work
            )
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
