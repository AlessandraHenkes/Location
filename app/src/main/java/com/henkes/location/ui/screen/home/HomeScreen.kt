package com.henkes.location.ui.screen.home

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.henkes.location.R
import com.henkes.location.service.LocationWorker
import com.henkes.location.ui.component.LButton
import com.henkes.location.ui.component.LSecondaryButton
import com.henkes.location.ui.component.LTopAppBar
import com.henkes.location.ui.theme.LTheme
import com.henkes.location.ui.theme.Theme

@Composable
fun HomeScreen(
    onNavigateToLocations: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LTheme.colors.background),
    ) {
        LTopAppBar(title = R.string.app_name)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp)
                .verticalScroll(state),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Option(
                option = R.string.alarm,
                onStart = viewModel::startAlarm,
                onStop = viewModel::stopAlarm,
            )
            Option(
                option = R.string.job,
                onStart = viewModel::startJob,
                onStop = viewModel::stopJob,
            )
            Option(
                option = R.string.worker,
                onStart = {
                    LocationWorker.start(context)
                },
                onStop = { LocationWorker.stop(context) },
            )
            Option(
                option = R.string.worker_periodic,
                onStart = {
                    LocationWorker.start(appContext = context, isPeriodic = true)
                },
                onStop = { LocationWorker.stop(appContext = context, isPeriodic = true) },
            )
            LButton(
                text = stringResource(id = R.string.see_locations),
                onClick = onNavigateToLocations,
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun Option(
    @StringRes option: Int,
    onStart: () -> Unit,
    onStop: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = option),
            style = LTheme.typography.heading2,
            color = LTheme.colors.textStrong
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            LSecondaryButton(text = stringResource(id = R.string.start), onClick = onStart)
            LSecondaryButton(text = stringResource(id = R.string.stop), onClick = onStop)
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    Theme {
        HomeScreen(onNavigateToLocations = {})
    }
}
