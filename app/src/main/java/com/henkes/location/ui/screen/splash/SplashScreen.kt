package com.henkes.location.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.henkes.location.R
import com.henkes.location.ui.theme.LTheme
import com.henkes.location.ui.theme.Theme
import com.henkes.location.ui.util.LaunchedEffectOnCondition

@Composable
fun SplashScreen(
    onNavigateToHome: (() -> Unit),
    viewModel: SplashViewModel = hiltViewModel()
) {
    val navigate = viewModel.navigateToHomeScreen.collectAsState()
    LaunchedEffectOnCondition(navigate.value) {
        onNavigateToHome()
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(LTheme.colors.background)
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_location_logo),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = stringResource(id = R.string.app_name),
            style = LTheme.typography.heading1,
            color = LTheme.colors.primary,
            modifier = Modifier.padding(
                horizontal = 36.dp,
                vertical = 34.dp
            )
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    Theme {
        SplashScreen(
            onNavigateToHome = {}
        )
    }
}
