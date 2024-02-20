package com.henkes.location.ui.screen.locations

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.henkes.location.R
import com.henkes.location.data.entity.LocationEntity
import com.henkes.location.ui.component.LButton
import com.henkes.location.ui.component.LTopAppBar
import com.henkes.location.ui.component.LVerticalSpacer
import com.henkes.location.ui.theme.LTheme
import com.henkes.location.ui.theme.Theme
import kotlin.random.Random

@Composable
fun LocationsScreen(
    onNavigateBack: () -> Unit,
    viewModel: LocationsViewModel = hiltViewModel()
) {
    val locations = viewModel.locations.collectAsState(initial = listOf())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LTheme.colors.background),
    ) {
        LTopAppBar(title = R.string.locations, onNavigateBack = onNavigateBack)
        LocationsScreenContent(locations = locations.value, onClear = viewModel::clear)
    }
}

@Composable
private fun LocationsScreenContent(
    locations: List<LocationEntity>,
    onClear: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
    ) {
        item {
            LVerticalSpacer(height = 16.dp)
        }
        items(
            locations,
            key = { loc -> loc.id ?: Random.nextInt() }
        ) { location ->
            Location(location = location)
        }
        item {
            LVerticalSpacer(height = 16.dp)
            LButton(
                text = stringResource(R.string.clear),
                onClick = onClear,
                modifier = Modifier.fillMaxWidth(),
            )
            LVerticalSpacer(height = 16.dp)
        }
    }
}

@Composable
private fun Location(location: LocationEntity) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        LVerticalSpacer(height = 8.dp)
        ItemDescription(description = R.string.latitude, value = location.latitude.toString())
        LVerticalSpacer(height = 4.dp)
        ItemDescription(description = R.string.longitude, value = location.longitude.toString())
        LVerticalSpacer(height = 4.dp)
        ItemDescription(description = R.string.time, value = location.time)
        LVerticalSpacer(height = 4.dp)
        ItemDescription(description = R.string.collected_method, value = location.collectionMethod)
        LVerticalSpacer(height = 4.dp)
        ItemDescription(
            description = R.string.collected_at,
            value = location.collectedAt,
            descriptionTextStyle = LTheme.typography.bodySmallStrong,
            valueTextStyle = LTheme.typography.bodySmallStrong,
            textColor = LTheme.colors.text,
        )
        LVerticalSpacer(height = 8.dp)
        Divider(color = LTheme.colors.accent)
    }
}

@Composable
private fun ItemDescription(
    @StringRes description: Int,
    value: String,
    descriptionTextStyle: TextStyle = LTheme.typography.bodyStrong,
    valueTextStyle: TextStyle = LTheme.typography.body,
    textColor: Color = LTheme.colors.textStrong
) {
    Row {
        Text(
            text = stringResource(id = description),
            style = descriptionTextStyle,
            color = textColor,
            modifier = Modifier.padding(end = 4.dp)
        )
        Text(
            text = value,
            style = valueTextStyle,
            color = textColor
        )
    }
}

@Preview
@Composable
fun LocationsScreenPreview() {
    Theme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(LTheme.colors.background)
        ) {
            LocationsScreenContent(
                locations = listOf(
                    LocationEntity(
                        latitude = 10.00,
                        longitude = 15.00,
                        time = "16/02/2024 12:03:03:03",
                        collectionMethod = "Alarm",
                        collectedAt = "16/02/2024 12:03:03:03"
                    )
                ),
                onClear = {},
            )
        }
    }
}
