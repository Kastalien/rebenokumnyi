package com.example.rebenokumnyi.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.rebenokumnyi.data.URDestination
import com.example.rebenokumnyi.ui.theme.appTypography
import java.util.Locale

@Composable
fun URTabRow(
    allScreens: List<URDestination>,
    onTabSelected: (URDestination) -> Unit,
    currentScreen: URDestination
) {
    Surface(
        Modifier
            .height(TabHeight)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier=Modifier.fillMaxSize()
        ) {
            Row(
                Modifier
                    .selectableGroup()
                    .height(IconHeight)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                allScreens.forEach { screen ->
                    URTab(
                        text = stringResource(id = screen.title),
                        icon = ImageVector.vectorResource(screen.icon),
                        onSelected = { onTabSelected(screen) },
                        selected = currentScreen == screen
                    )
                }
            }
            Text(
                stringResource(id = currentScreen.title).uppercase(Locale.getDefault()),
                color = MaterialTheme.colorScheme.onPrimary,
                style = appTypography.labelMedium
            )
        }
    }
}

@Composable
private fun RowScope.URTab(
    text: String, icon: ImageVector, onSelected: () -> Unit, selected: Boolean
) {
    val color = MaterialTheme.colorScheme.onSurface
    val durationMillis = if (selected) TabFadeInAnimationDuration else TabFadeOutAnimationDuration
    val animSpec = remember {
        tween<Color>(
            durationMillis = durationMillis,
            easing = LinearEasing,
            delayMillis = TabFadeInAnimationDelay
        )
    }
    val tabTintColor by animateColorAsState(
        targetValue = if (selected) color else color.copy(alpha = InactiveTabOpacity),
        animationSpec = animSpec,
        label = ""
    )
    Box(
        modifier = Modifier
            .padding(4.dp)
            .animateContentSize()
            .fillMaxHeight()
            .weight(1F)
            .selectable(
                selected = selected,
                onClick = onSelected,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false, radius = Dp.Unspecified, color = Color.Unspecified
                )
            )
            .clearAndSetSemantics { contentDescription = text },
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            ElevatedCard(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                ),
                modifier = Modifier.fillMaxHeight()
            ) {
                Image(
                    imageVector = icon, contentDescription = text, modifier = Modifier.fillMaxHeight()
                )
            }
        } else {
            Image(
                imageVector = icon, contentDescription = text, modifier = Modifier.fillMaxHeight(0.7F)
            )
        }
    }
}

private val TabHeight = 84.dp
private val IconHeight = 64.dp
private const val InactiveTabOpacity = 0.60f

private const val TabFadeInAnimationDuration = 150
private const val TabFadeInAnimationDelay = 100
private const val TabFadeOutAnimationDuration = 100