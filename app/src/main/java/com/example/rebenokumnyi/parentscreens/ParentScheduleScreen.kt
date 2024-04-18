package com.example.rebenokumnyi.parentscreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rebenokumnyi.R
import com.example.rebenokumnyi.teacherscreens.DaySelector
import com.example.rebenokumnyi.teacherscreens.ScheduleList
import com.example.rebenokumnyi.viewmodels.ParentScheduleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParentScheduleScreen(ScheduleViewModel: ParentScheduleViewModel = viewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
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
            if (ScheduleViewModel.visibleSchedule.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxSize(),
                    border = BorderStroke(2.dp, Color.Black),
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = if (ScheduleViewModel.selectedGroupId==-1)
                                        stringResource(id = R.string.group_not_selected)
                                    else
                                        stringResource(id = R.string.no_lessons)
                        )
                    }
                }
            } else {
                ScheduleList(
                    ScheduleViewModel.visibleSchedule, ScheduleViewModel.isLoading, false
                ) { ScheduleViewModel.loadSchedule() }
            }
        }
    }
    DisposableEffect(Unit) {
        ScheduleViewModel.loadSchedule()
        onDispose {}
    }
}