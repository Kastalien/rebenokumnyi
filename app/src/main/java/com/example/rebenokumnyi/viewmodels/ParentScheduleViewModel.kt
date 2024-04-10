package com.example.rebenokumnyi.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rebenokumnyi.data.AppData
import com.example.rebenokumnyi.data.Group
import com.example.rebenokumnyi.data.Schedule
import com.example.rebenokumnyi.data.Subject
import com.example.rebenokumnyi.data.currentRole
import com.example.rebenokumnyi.data.groupSchedule
import com.example.rebenokumnyi.data.groups
import com.example.rebenokumnyi.data.loadSubjects
import kotlinx.coroutines.launch


class ParentScheduleViewModel : ViewModel() {
    var visibleSchedule = emptyList<Schedule>().toMutableStateList()
    var isLoading by mutableStateOf(true)
    var selectedGroupId by mutableStateOf(AppData.currentChild.groupId)
    var dayOfWeek by mutableIntStateOf(1)

    fun getSchedule() {
        visibleSchedule.clear()
        visibleSchedule.addAll(groupSchedule.filter { it.dayOfWeek == dayOfWeek }.sortedBy { it.start })
    }

    fun loadSchedule() {
        isLoading = true
        var isScheduleLoading=true
        var isSubjectLoading=true
        visibleSchedule.clear()
        viewModelScope.launch {
            com.example.rebenokumnyi.data.loadSchedule(selectedGroupId) {
                visibleSchedule.addAll(groupSchedule.filter { it.dayOfWeek == dayOfWeek }
                    .sortedBy { it.start })
                isScheduleLoading = false
                if (!isSubjectLoading)
                    isLoading = false
            }
        }
        viewModelScope.launch {
            loadSubjects {
                isSubjectLoading=false
                if (!isScheduleLoading)
                    isLoading = false
            }
        }
    }

    fun prevDay() {
        if (dayOfWeek>1)
            dayOfWeek--
        getSchedule()
    }
    fun nextDay() {
        if (dayOfWeek<7)
            dayOfWeek++
        getSchedule()
    }
}