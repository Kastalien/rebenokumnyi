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
import com.example.rebenokumnyi.data.Student
import com.example.rebenokumnyi.data.groups
import com.example.rebenokumnyi.ui.theme.appTypography

@Composable
fun URStudentSelector(students: List<Student>, onSelect: (Student) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        border = BorderStroke(2.dp, Color.Black),
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(students) { index, student ->
                Row(
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Button(
                        onClick = { onSelect(student) },
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(30)
                    ) {
                        Text(text = "${student.name}", style = appTypography.bodyMedium)
                    }
                }
            }
        }
    }
}