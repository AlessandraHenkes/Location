package com.henkes.location.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import kotlinx.coroutines.CoroutineScope

@NonRestartableComposable
@Composable
fun LaunchedEffectOnCondition(
    condition: Boolean, block: suspend CoroutineScope.() -> Unit
) {
    if (condition) LaunchedEffect(Unit) {
        block.invoke(this)
    }
}
