package com.example.rebenokumnyi.parentscreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rebenokumnyi.R
import com.example.rebenokumnyi.teacherscreens.DateSelector
import com.example.rebenokumnyi.teacherscreens.JournalList
import com.example.rebenokumnyi.viewmodels.ParentJournalViewModel

@Composable
fun ParentJournalScreen(JournalViewModel: ParentJournalViewModel = viewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (JournalViewModel.isEmpty) {
            Text(stringResource(id = R.string.nonGroupForTeacher), textAlign = TextAlign.Center)
        } else {
            DateSelector(JournalViewModel.currentDate,
                { JournalViewModel.prevDay() },
                { JournalViewModel.nextDay() })
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
            ) {
                JournalList(JournalViewModel.visibleSchedule,
                    JournalViewModel.isLoading,
                    false,
                    JournalViewModel.selectedStudent == "",
                    { _, _, _: String -> })
                {}
            }
        }
        DisposableEffect(Unit) {
            JournalViewModel.loadJournal()
            onDispose {}
        }
    }
}
