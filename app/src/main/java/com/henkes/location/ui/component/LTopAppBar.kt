package com.henkes.location.ui.component

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.henkes.location.R
import com.henkes.location.ui.theme.LTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LTopAppBar(
    @StringRes title: Int,
    onNavigateBack: (() -> Unit)? = null,
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_location_logo),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                LHorizontalSpacer(width = 4.dp)
                Text(
                    text = stringResource(id = title),
                    style = LTheme.typography.heading2,
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = LTheme.colors.primary,
            titleContentColor = LTheme.colors.textAction,
            navigationIconContentColor = LTheme.colors.textAction,
        ),
        navigationIcon = getNavigationIcon(onNavigateBack)
    )
}

private fun getNavigationIcon(onNavigateBack: (() -> Unit)?): @Composable () -> Unit {
    return if (onNavigateBack == null) ({}) else ({
        IconButton(onClick = onNavigateBack) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.navigate_back),
            )
        }
    })
}
