package com.henkes.location

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.henkes.location.notification.NotificationUtil
import com.henkes.location.service.PermissionsUtil
import com.henkes.location.ui.navigation.NavigationHost
import com.henkes.location.ui.theme.Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var permissionsUtil: PermissionsUtil

    private val requestNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) NotificationUtil.createChannel(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNotifications()
        setContent {
            Theme {
                Scaffold { paddingValues ->
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        NavigationHost()
                    }
                }
            }
        }
    }

    private fun checkNotifications() {
        permissionsUtil.checkNotificationPermission(
            activity = this,
            onRequestPermissionRationale = {
                // TODO
            },
            onRequestPermission = { permission ->
                requestNotificationPermissionLauncher.launch(permission)
            },
            onPermissionGranted = {
                NotificationUtil.createChannel(applicationContext)
            }
        )
    }

}
