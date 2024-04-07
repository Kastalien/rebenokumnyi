package com.example.rebenokumnyi.teacherscreens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
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
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rebenokumnyi.R
import com.example.rebenokumnyi.components.TimePickerDialog
import com.example.rebenokumnyi.components.URButton
import com.example.rebenokumnyi.components.URInputButton
import com.example.rebenokumnyi.components.URListButton
import com.example.rebenokumnyi.components.UROutlineTextField
import com.example.rebenokumnyi.components.URSelectGroup
import com.example.rebenokumnyi.components.URSubjectSelector
import com.example.rebenokumnyi.data.AppData
import com.example.rebenokumnyi.data.Schedule
import com.example.rebenokumnyi.data.daysOfWeek
import com.example.rebenokumnyi.data.subjects
import com.example.rebenokumnyi.ui.theme.appTypography
import com.example.rebenokumnyi.viewmodels.ScheduleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherScheduleScreen(ScheduleViewModel: ScheduleViewModel = viewModel()) {
    var isSelectGroup by remember { mutableStateOf(false) }
    var isSelectSubject by remember { mutableStateOf(false) }
    var isPickTime by remember { mutableStateOf(false) }
    val timePickerState = rememberTimePickerState(
        initialHour = 9, initialMinute = 30, is24Hour = true
    )
    var newScheduleTime by remember { mutableStateOf("09:30") }
    var newDuration by remember { mutableStateOf("40") }
    var newSubject by remember { mutableStateOf(AppData.context.getString(R.string.select_lesson)) }
    var newSubjectId by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (ScheduleViewModel.isEmpty) {
            Text(stringResource(id = R.string.nonGroupForTeacher), textAlign = TextAlign.Center)
        } else {
            if (isSelectGroup) {
                URSelectGroup(ScheduleViewModel.teacherGroups) {
                    ScheduleViewModel.selectedGroup = it
                    isSelectGroup = false
                    ScheduleViewModel.loadSchedule()
                }
            } else if (isSelectSubject) {
                URSubjectSelector(subjects, {
                    newSubject = it.name
                    newSubjectId = it.id
                    isSelectSubject = false
                }, {
                    val createdSubject = ScheduleViewModel.addNewSubject(it)
                    newSubject = createdSubject?.name
                        ?: AppData.context.getString(R.string.error_creating_subject)
                    newSubjectId = createdSubject?.id ?: ""
                    isSelectSubject = false
                })
            } else if (isPickTime) {
                TimePickerDialog(onDismissRequest = { }, confirmButton = {
                    TextButton(onClick = {
                        newScheduleTime = "${
                            timePickerState.hour.toString().padStart(2, '0')
                        }:${timePickerState.minute.toString().padStart(2, '0')}"
                        isPickTime = false
                    }) { Text(stringResource(id = R.string.select_time)) }
                }, dismissButton = {
                    TextButton(onClick = {
                        isPickTime = false
                    }) { Text(stringResource(id = R.string.cancel_time)) }
                }) {
                    TimePicker(state = timePickerState)
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
                            Text(ScheduleViewModel.selectedGroup.name)
                        }
                        DaySelector(ScheduleViewModel.dayOfWeek,
                            { ScheduleViewModel.prevDay() },
                            { ScheduleViewModel.nextDay() })
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F)
                ) {
                    ScheduleList(
                        ScheduleViewModel.visibleSchedule, ScheduleViewModel.isLoading
                    ) { ScheduleViewModel.loadSchedule() }
                }
                Column(
                    modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start
                ) {
                    var isErrorSubject by remember { mutableStateOf(false) }
                    var isErrorDuration by remember { mutableStateOf(false) }
                    Text(stringResource(id = R.string.add_lesson_in_schedule))
                    URInputButton(
                        newSubject,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                    ) {
                        isSelectSubject = true
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(stringResource(id = R.string.time_start))
                        Spacer(Modifier.width(4.dp))
                        URInputButton(
                            text = newScheduleTime, modifier = Modifier
                                .height(45.dp)
                                .width(60.dp)
                        ) {
                            isPickTime = true
                        }
                        Spacer(Modifier.width(10.dp))
                        Text(stringResource(id = R.string.duration))
                        Spacer(Modifier.width(4.dp))
                        UROutlineTextField("", newDuration, true) { newDuration = it }
                        Spacer(Modifier.width(10.dp))
                        Text(stringResource(id = R.string.min))
                    }

                    Spacer(modifier = Modifier.height(5.dp))
                    if (isErrorDuration) Text(
                        text = stringResource(id = R.string.error_schedule_no_duration),
                        color = Color.Red
                    )
                    if (isErrorSubject) Text(
                        text = stringResource(id = R.string.error_schedule_no_subject),
                        color = Color.Red
                    )
                    URButton(
                        stringResource(id = R.string.add),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                    ) {
                        if (!newDuration.isDigitsOnly()) isErrorDuration = true
                        else if (newSubjectId == "") isErrorSubject = true
                        else {
                            ScheduleViewModel.addNewLesson(
                                newSubjectId, newScheduleTime, newDuration.toInt()
                            )
                            isErrorDuration=false
                            isErrorSubject=false
                            ScheduleViewModel.loadSchedule()
                        }
                    }

                }
            }
            DisposableEffect(Unit) {
                ScheduleViewModel.loadSchedule()
                onDispose {}
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleList(daySchedule: MutableList<Schedule>, isLoading: Boolean, onUpdate: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxSize(),
        border = BorderStroke(2.dp, Color.Black),
    ) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(100.dp))
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(daySchedule) { index, schedule ->
                    Row(
                        modifier = Modifier.height(30.dp)
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
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row {
                                Text(
                                    text = "${schedule.start}",
                                    style = appTypography.bodyMedium,
                                    modifier = Modifier.width(40.dp)
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "${schedule.getSubject()?.name?:""}"
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    text = "(${stringResource(
                                        R.string.lesson_duration_time, schedule.duration
                                    )})"
                                )
                            }
                        }
                        URListButton(icon = ImageVector.vectorResource(R.drawable.delete)) {
                            schedule.remove()
                            onUpdate()
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ColumnScope.DaySelector(dayOfWeek: Int, onLeft: () -> Unit, onRight: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onLeft, enabled = dayOfWeek != 1
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.left),
                contentDescription = "",
                modifier = Modifier.width(60.dp)
            )
        }
        Text(text = daysOfWeek[dayOfWeek]?.let {
            stringResource(
                id = it
            )
        } ?: "", modifier = Modifier.weight(1F), textAlign = TextAlign.Center)
        IconButton(
            onClick = onRight, enabled = dayOfWeek != 7
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.right),
                contentDescription = "",
                modifier = Modifier.width(60.dp)
            )
        }
    }
}