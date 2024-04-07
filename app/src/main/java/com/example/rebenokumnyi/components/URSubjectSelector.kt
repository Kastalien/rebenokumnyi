package com.example.rebenokumnyi.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.rebenokumnyi.R
import com.example.rebenokumnyi.data.Subject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun URSubjectSelector(
    subjects: List<Subject>,
    onSelect: (Subject) -> Unit,
    onNew: (String) -> Unit
) {
    var newLessonName by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .weight(1F)
                .fillMaxWidth()
                .padding(5.dp),
            border = BorderStroke(2.dp, Color.Black),
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(subjects) { index, subject ->
                    Row(
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        URButton("${subject.name}", Modifier.fillMaxSize()) {
                            onSelect(subject)
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            URButton(
                text = stringResource(id = R.string.add_lesson), modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            ) {
                if (newLessonName!="")
                    onNew(newLessonName)
            }
            Spacer(modifier = Modifier.height(5.dp))
            UROutlineTextField(
                placeholder = stringResource(R.string.enter_new_group_name),
                value = newLessonName
            ) { newLessonName = it }
        }
    }
}