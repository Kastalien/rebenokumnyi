package com.example.rebenokumnyi.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.URListButton(icon: ImageVector, onClickButton: () -> Unit) {
    Box(
        modifier = Modifier
            .size(35.dp, 35.dp)
            .padding(4.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        FilledIconButton(
            onClick = onClickButton,
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
        ) {
            Image(
                imageVector = icon,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp)
            )
        }
    }
}