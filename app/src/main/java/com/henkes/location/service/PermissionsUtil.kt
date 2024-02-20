package com.henkes.location.service

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

class PermissionsUtil(
    context: Context
) {

    fun checkNotificationPermission(
        activity: Activity,
        onPermissionGranted: () -> Unit,
        onRequestPermissionRationale: () -> Unit,
        onRequestPermission: (permission: String) -> Unit
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
            && ContextCompat.checkSelfPermission(
                activity.applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (
                activity.shouldShowRequestPermissionRationale(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            ) {
                onRequestPermissionRationale()
            } else {
                onRequestPermission(Manifest.permission.POST_NOTIFICATIONS)
            }
        } else onPermissionGranted()
    }

}
