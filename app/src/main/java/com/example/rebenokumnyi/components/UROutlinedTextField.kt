package com.example.rebenokumnyi.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.example.rebenokumnyi.ui.theme.md_theme_light_primary
import com.example.rebenokumnyi.ui.theme.md_theme_light_secondaryContainer

@Composable
fun UROutlineTextField(placeholder: String, value: String, isNumber:Boolean = false,modifier: Modifier = Modifier, isSingleLine: Boolean = true, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,

        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isNumber) KeyboardType.Number else KeyboardType.Text
        ),
        singleLine = isSingleLine,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = md_theme_light_secondaryContainer,
            unfocusedContainerColor = md_theme_light_secondaryContainer,
            disabledContainerColor = md_theme_light_secondaryContainer,
        ),
        modifier = modifier
            .fillMaxWidth()
    )
}