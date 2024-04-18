package com.example.rebenokumnyi.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rebenokumnyi.data.AppData
import com.example.rebenokumnyi.data.Journal
import com.example.rebenokumnyi.data.Schedule
import com.example.rebenokumnyi.data.groupSchedule
import com.example.rebenokumnyi.data.loadSchedule
import com.example.rebenokumnyi.data.loadSelectedJournal
import com.example.rebenokumnyi.data.loadSubjects
import com.example.rebenokumnyi.data.subjects
import kotlinx.coroutines.launch
import java.time.LocalDate

class ParentJournalViewModel : ViewModel() {
    var visibleSchedule = emptyList<Schedule>().toMutableStateList()
    var isLoading by mutableStateOf(false)
    var isEmpty by mutableStateOf(false)
    var selectedGroup by mutableStateOf(AppData.currentChild.groupId)
    var selectedStudent by mutableStateOf(AppData.currentChild.id)
    var currentDate: LocalDate by mutableStateOf(LocalDate.now())

    fun loadJournal() {
        if ((selectedStudent == "") || ((selectedGroup == -1))) {
            isEmpty = true
            return
        }
        isLoading = true
        var loadingCount = 2
        if (subjects.isEmpty()) loadingCount++
        visibleSchedule.clear()
        if (subjects.isEmpty()) {
            viewModelScope.launch {
                loadSubjects {
                    loadingCount--
                    if (loadingCount == 0) isLoading = false
                }
            }
        }
        viewModelScope.launch {
            loadSchedule(selectedGroup) {
                visibleSchedule.addAll(groupSchedule.filter { it.dayOfWeek == currentDate.dayOfWeek.value }
                    .sortedBy { it.start })
                loadingCount--
                if (loadingCount == 0) isLoading = false
            }
        }
        viewModelScope.launch {
            loadSelectedJournal(selectedStudent, currentDate.toString()) {
                loadingCount--
                if (loadingCount == 0) isLoading = false
            }
        }
    }

    fun prevDay() {
        currentDate = currentDate.minusDays(1)
        loadJournal()
    }

    fun nextDay() {
        if (currentDate < LocalDate.now()) currentDate = currentDate.plusDays(1)
        loadJournal()
    }
}