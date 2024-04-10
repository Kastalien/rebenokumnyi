package com.example.rebenokumnyi.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.rebenokumnyi.data.Group
import com.example.rebenokumnyi.ui.theme.appTypography

@Composable
fun URGroupSelector(groups: List<Group>, onSelect: (Group) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        border = BorderStroke(2.dp, Color.Black),
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(groups) { index, group ->
                Row(
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Button(
                        onClick = { onSelect(group) },
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(30)
                    ) {
                        Text(text = "${group.name}", style = appTypography.bodyMedium)
                    }
                }
            }
        }
    }
}