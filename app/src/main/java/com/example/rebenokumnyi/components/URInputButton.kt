package com.example.rebenokumnyi.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rebenokumnyi.ui.theme.md_theme_light_primary
import com.example.rebenokumnyi.ui.theme.md_theme_light_tertiaryContainer

@Composable
fun URInputButton(text: String, modifier:Modifier=Modifier, onClick: () -> Unit) {
    TextButton(
        onClick =onClick,
        modifier = modifier,
        shape = RoundedCornerShape(20),
        border = BorderStroke(1.dp, md_theme_light_primary),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = md_theme_light_tertiaryContainer)
    ) {
        Text(text)
    }
}
