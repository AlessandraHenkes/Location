package com.henkes.location.ui.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SPLASH_DELAY = 2_000L // 2 seconds

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _navigateToHomeScreen = MutableStateFlow(false)
    val navigateToHomeScreen: StateFlow<Boolean> = _navigateToHomeScreen

    init {
        viewModelScope.launch {
            delay(SPLASH_DELAY)
            _navigateToHomeScreen.value = true
        }
    }

}
