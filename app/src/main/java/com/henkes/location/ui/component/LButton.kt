package com.henkes.location.ui.component

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.henkes.location.ui.theme.LTheme
import com.henkes.location.ui.theme.Theme

enum class ButtonType {
    PRIMARY, SECONDARY
}

@Composable
fun LButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.PRIMARY,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = getColor(type = type),
            contentColor = LTheme.colors.textAction,
        )
    ) {
        Text(
            text = text,
            style = LTheme.typography.bodyStrong,
        )
    }
}

@Composable
fun LSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LButton(
        text = text,
        onClick = onClick,
        type = ButtonType.SECONDARY,
        modifier = modifier
    )
}

@Composable
private fun getColor(type: ButtonType) =
    if (type == ButtonType.PRIMARY) LTheme.colors.primary else LTheme.colors.accent

@Preview
@Composable
fun LButtonPreview() {
    Theme {
        LButton(text = "Preview", onClick = {}, type = ButtonType.PRIMARY)
        LButton(text = "Preview", onClick = {}, type = ButtonType.SECONDARY)
    }
}
