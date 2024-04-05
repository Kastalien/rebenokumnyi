package com.example.compose

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.rebenokumnyi.ui.theme.appTypography
import com.example.rebenokumnyi.ui.theme.md_theme_light_background
import com.example.rebenokumnyi.ui.theme.md_theme_light_error
import com.example.rebenokumnyi.ui.theme.md_theme_light_errorContainer
import com.example.rebenokumnyi.ui.theme.md_theme_light_inverseOnSurface
import com.example.rebenokumnyi.ui.theme.md_theme_light_inversePrimary
import com.example.rebenokumnyi.ui.theme.md_theme_light_inverseSurface
import com.example.rebenokumnyi.ui.theme.md_theme_light_onBackground
import com.example.rebenokumnyi.ui.theme.md_theme_light_onError
import com.example.rebenokumnyi.ui.theme.md_theme_light_onErrorContainer
import com.example.rebenokumnyi.ui.theme.md_theme_light_onPrimary
import com.example.rebenokumnyi.ui.theme.md_theme_light_onPrimaryContainer
import com.example.rebenokumnyi.ui.theme.md_theme_light_onSecondary
import com.example.rebenokumnyi.ui.theme.md_theme_light_onSecondaryContainer
import com.example.rebenokumnyi.ui.theme.md_theme_light_onSurface
import com.example.rebenokumnyi.ui.theme.md_theme_light_onSurfaceVariant
import com.example.rebenokumnyi.ui.theme.md_theme_light_onTertiary
import com.example.rebenokumnyi.ui.theme.md_theme_light_onTertiaryContainer
import com.example.rebenokumnyi.ui.theme.md_theme_light_outline
import com.example.rebenokumnyi.ui.theme.md_theme_light_outlineVariant
import com.example.rebenokumnyi.ui.theme.md_theme_light_primary
import com.example.rebenokumnyi.ui.theme.md_theme_light_primaryContainer
import com.example.rebenokumnyi.ui.theme.md_theme_light_scrim
import com.example.rebenokumnyi.ui.theme.md_theme_light_secondary
import com.example.rebenokumnyi.ui.theme.md_theme_light_secondaryContainer
import com.example.rebenokumnyi.ui.theme.md_theme_light_surface
import com.example.rebenokumnyi.ui.theme.md_theme_light_surfaceTint
import com.example.rebenokumnyi.ui.theme.md_theme_light_surfaceVariant
import com.example.rebenokumnyi.ui.theme.md_theme_light_tertiary
import com.example.rebenokumnyi.ui.theme.md_theme_light_tertiaryContainer


private val lightColors = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_primaryContainer,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_secondaryContainer,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)

@Composable
fun RebenokumnyiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = lightColors
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = appTypography,
        content = content
    )

}