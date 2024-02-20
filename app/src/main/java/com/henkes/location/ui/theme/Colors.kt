package com.henkes.location.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val BlueLight = Color(0xFFB2CBF5)
val GreenLight = Color(0xFFB1E6B5)
val RedLight = Color(0xFFFDB8BF)
val OrangeLight = Color(0xFFF8DCB4)

val Pink = Color(0xFFFF5194)
val PinkDark = Color(0xFFB7257F)
val NeutralLighter = Color(0xFFF8F7F7)
val NeutralLight = Color(0xFFECECEC)
val NeutralDark = Color(0xFF2F3036)
val NeutralDarkest = Color(0xFF1F2024)

@Immutable
data class Colors(
    val background: Color,
    val backgroundInverse: Color,
    val primary: Color,
    val accent: Color,
    val text: Color,
    val textStrong: Color,
    val textInverse: Color,
    val textAction: Color,
    val information: Color,
    val success: Color,
    val error: Color,
    val warning: Color
)

val LocalColors = staticCompositionLocalOf {
    Colors(
        background = Color.Unspecified,
        backgroundInverse = Color.Unspecified,
        primary = Color.Unspecified,
        accent = Color.Unspecified,
        text = Color.Unspecified,
        textStrong = Color.Unspecified,
        textInverse = Color.Unspecified,
        textAction = Color.Unspecified,
        information = Color.Unspecified,
        success = Color.Unspecified,
        error = Color.Unspecified,
        warning = Color.Unspecified
    )
}

val LightThemeColors = Colors(
    background = NeutralLighter,
    backgroundInverse = NeutralDarkest,
    primary = PinkDark,
    accent = Pink,
    text = NeutralDark,
    textStrong = NeutralDarkest,
    textInverse = NeutralLight,
    textAction = NeutralLight,
    information = BlueLight,
    success = GreenLight,
    error = RedLight,
    warning = OrangeLight
)

val DarkThemeColors = Colors(
    background = NeutralDarkest,
    backgroundInverse = NeutralLighter,
    primary = PinkDark,
    accent = Pink,
    text = NeutralLight,
    textStrong = NeutralLighter,
    textInverse = NeutralDark,
    textAction = NeutralLight,
    information = BlueLight,
    success = GreenLight,
    error = RedLight,
    warning = OrangeLight
)
