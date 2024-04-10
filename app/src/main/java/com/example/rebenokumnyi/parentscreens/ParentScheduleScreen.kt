package com.example.rebenokumnyi.parentscreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rebenokumnyi.R
import com.example.rebenokumnyi.components.TimePickerDialog
import com.example.rebenokumnyi.components.URButton
import com.example.rebenokumnyi.components.URGroupSelector
import com.example.rebenokumnyi.components.URInputButton
import com.example.rebenokumnyi.components.UROutlineTextField
import com.example.rebenokumnyi.components.URSubjectSelector
import com.example.rebenokumnyi.data.AppData
import com.example.rebenokumnyi.data.subjects
import com.example.rebenokumnyi.teacherscreens.DaySelector
import com.example.rebenokumnyi.teacherscreens.ScheduleList
import com.example.rebenokumnyi.viewmodels.ParentScheduleViewModel
import com.example.rebenokumnyi.viewmodels.ScheduleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentScheduleScreen(ScheduleViewModel: ParentScheduleViewModel = viewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
         Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
             Column(horizontalAlignment = Alignment.Start) {
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
                        ScheduleViewModel.visibleSchedule, ScheduleViewModel.isLoading, false
                    ) { ScheduleViewModel.loadSchedule() }
                }
         }
            DisposableEffect(Unit) {
                ScheduleViewModel.loadSchedule()
                onDispose {}
            }
        }