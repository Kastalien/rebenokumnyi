package com.example.rebenokumnyi.teacherscreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rebenokumnyi.R
import com.example.rebenokumnyi.components.URGroupSelector
import com.example.rebenokumnyi.components.URListButton
import com.example.rebenokumnyi.components.UROutlineTextField
import com.example.rebenokumnyi.components.URStudentSelector
import com.example.rebenokumnyi.data.Schedule
import com.example.rebenokumnyi.data.Student
import com.example.rebenokumnyi.data.daysOfWeek
import com.example.rebenokumnyi.ui.theme.appTypography
import com.example.rebenokumnyi.viewmodels.JournalViewModel
import java.time.LocalDate

@Composable
fun TeacherJournalScreen(JournalViewModel: JournalViewModel = viewModel()) {
    var isSelectGroup by remember { mutableStateOf(false) }
    var isSelectStudent by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (JournalViewModel.isEmpty) {
            Text(stringResource(id = R.string.nonGroupForTeacher), textAlign = TextAlign.Center)
        } else {
            if (isSelectGroup) {
                URGroupSelector(JournalViewModel.teacherGroups) {
                    JournalViewModel.selectedGroup = it
                    JournalViewModel.selectedStudent = Student()
                    isSelectGroup = false
                    JournalViewModel.loadJournal()
                }
            } else if (isSelectStudent) {
                URStudentSelector(JournalViewModel.groupStudents) {
                    JournalViewModel.selectedStudent = it
                    isSelectStudent = false
                    JournalViewModel.loadJournal()
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(stringResource(id = R.string.aGroup))
                        Button(
                            onClick = {
                                isSelectGroup = true
                            }, modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                        ) {
                            Text(JournalViewModel.selectedGroup.name)
                        }
                        Text(stringResource(id = R.string.aStudent))
                        Button(
                            onClick = {
                                isSelectStudent = true
                            }, modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                        ) {
                            Text(JournalViewModel.selectedStudent.name)
                        }
                        DateSelector(JournalViewModel.currentDate,
                            { JournalViewModel.prevDay() },
                            { JournalViewModel.nextDay() })
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F)
                ) {
                    JournalList(JournalViewModel.visibleSchedule,
                        JournalViewModel.isLoading,
                        JournalViewModel.selectedStudent.id == "",
                        { journalId, scheduleId: String, newNote: String ->
                            JournalViewModel.saveNote(journalId, scheduleId, newNote)
                        }) { JournalViewModel.loadJournal() }
                }
            }
            DisposableEffect(Unit) {
                JournalViewModel.loadJournal()
                onDispose {}
            }
        }
    }
}

@Composable
fun ColumnScope.DateSelector(date: LocalDate, onLeft: () -> Unit, onRight: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onLeft
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.left),
                contentDescription = "",
                modifier = Modifier.width(60.dp)
            )
        }
        Text(text = ("$date " + daysOfWeek[date.dayOfWeek.value]?.let {
            stringResource(
                id = it
            )
        }), modifier = Modifier.weight(1F), textAlign = TextAlign.Center)
        IconButton(
            onClick = onRight, enabled = date != LocalDate.now()
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.right),
                contentDescription = "",
                modifier = Modifier.width(60.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalList(
    daySchedule: MutableList<Schedule>,
    isLoading: Boolean,
    isStudentSelected: Boolean,
    onNewNote: (String?, String, String) -> Unit,
    onUpdate: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxSize(),
        border = BorderStroke(2.dp, Color.Black),
    ) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(100.dp))
            }
        } else if (isStudentSelected) {
            Row(horizontalArrangement = Arrangement.Center){Text(text = stringResource(id = R.string.select_student_and_group))}
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(daySchedule) { index, schedule ->
                    var isEdit by remember { mutableStateOf(false) }
                    Row(
                        modifier = Modifier.height(if (isEdit) 120.dp else 60.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(0.11F)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "${index + 1}.")
                        }
                        Column(
                            modifier = Modifier
                                .weight(0.9F)
                                .fillMaxHeight()
                                .padding(end = 3.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "${schedule.getSubject()?.name ?: ""}",
                                style = appTypography.bodyLarge
                            )
                            Card(
                                modifier = Modifier.fillMaxSize(),
                                border = BorderStroke(1.dp, Color.Black)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(3.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    var newNote by remember { mutableStateOf("${schedule.getNote() ?: ""}") }
                                    if (!isEdit) {
                                        Text(
                                            text = "${schedule.getNote() ?: stringResource(id = R.string.enter_note)}",
                                            style = appTypography.bodySmall
                                        )
                                        URListButton(icon = ImageVector.vectorResource(R.drawable.edit)) {
                                            isEdit = true
                                        }
                                    } else {
                                        Box(
                                            Modifier
                                                .fillMaxWidth(0.8F)
                                                .fillMaxHeight()
                                        ) {
                                            UROutlineTextField(placeholder = "${
                                                stringResource(
                                                    id = R.string.enter_note
                                                )
                                            }",
                                                value = newNote,
                                                modifier = Modifier.fillMaxHeight(),
                                                onValueChange = { newNote = it })
                                        }
                                        URListButton(icon = ImageVector.vectorResource(R.drawable.save)) {
                                            onNewNote(
                                                schedule.getNoteId(), schedule.id, newNote
                                            )
                                            onUpdate()
                                            isEdit = false
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

