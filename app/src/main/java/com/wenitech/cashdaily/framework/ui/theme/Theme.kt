package com.wenitech.cashdaily.framework.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Blue200,
    primaryVariant = Blue700,
    secondary = Teal200,
    onSecondary = OnSecondaryLight,
    background = backgroundDark,
    onBackground = onBackgroundDart,
    surface = surfacedDark,
    onSurface = onSurfaceDart
)

private val LightColorPalette = lightColors(
    primary = SurfaceLight,
    primaryVariant = SurfaceLight,
    secondary = Blue500,
    onSecondary= SurfaceLight,
    onPrimary = TextLight,
    surface = SurfaceLight,
    onSurface = TextLight,
    background = BackgroundLight

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun CashDailyTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}