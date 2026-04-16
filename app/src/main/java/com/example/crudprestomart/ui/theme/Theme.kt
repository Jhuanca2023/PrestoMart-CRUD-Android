package com.example.crudprestomart.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrestoRed,
    secondary = PrestoYellow,
    tertiary = PrestoWhite,
    background = PrestoDarkGray,
    surface = PrestoDarkGray,
    onPrimary = Color.White,
    onSecondary = PrestoDarkGray,
    onTertiary = PrestoDarkGray,
    onBackground = PrestoWhite,
    onSurface = PrestoWhite
)

private val LightColorScheme = lightColorScheme(
    primary = PrestoRed,
    secondary = PrestoYellow,
    tertiary = PrestoWhite,
    background = PrestoLightGray,
    surface = PrestoWhite,
    onPrimary = Color.White,
    onSecondary = PrestoDarkGray,
    onTertiary = PrestoDarkGray,
    onBackground = PrestoDarkGray,
    onSurface = PrestoDarkGray
)

@Composable
fun CrudPrestomartTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}