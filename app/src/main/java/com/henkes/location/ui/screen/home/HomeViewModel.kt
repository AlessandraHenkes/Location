package com.henkes.location.ui.screen.home

import androidx.lifecycle.ViewModel
import com.henkes.location.service.alarm.AlarmScheduler
import com.henkes.location.service.job.LocationJobScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val alarmScheduler: AlarmScheduler,
    private val jobScheduler: LocationJobScheduler,
) : ViewModel() {

    fun startAlarm() {
        alarmScheduler.schedule()
    }

    fun stopAlarm() {
        alarmScheduler.cancel()
    }

    fun startJob() {
        jobScheduler.schedule()
    }

    fun stopJob() {
        jobScheduler.cancel()
    }

    fun checkPermissions() {

    }

}
