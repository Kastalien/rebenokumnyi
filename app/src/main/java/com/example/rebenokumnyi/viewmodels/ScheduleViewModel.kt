package com.example.rebenokumnyi.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rebenokumnyi.data.Group
import com.example.rebenokumnyi.data.Schedule
import com.example.rebenokumnyi.data.currentRole
import com.example.rebenokumnyi.data.groupSchedule
import com.example.rebenokumnyi.data.groups
import com.example.rebenokumnyi.data.loadSchedule
import kotlinx.coroutines.launch

class ScheduleViewModel : ViewModel() {
    val teacherGroups = groups.filter { it.userId == currentRole.userId }
    var visibleSchedule = emptyList<Schedule>().toMutableStateList()
    var isLoading by mutableStateOf(true)
    var isEmpty by mutableStateOf(false)
    var selectedGroup by mutableStateOf(if (teacherGroups.isEmpty()) Group() else teacherGroups[0])
    var dayOfWeek by mutableIntStateOf(1)

    fun getSchedule() {
        visibleSchedule.clear()
        visibleSchedule.addAll(groupSchedule.filter { it.dayOfWeek == dayOfWeek })
    }

    fun loadSchedule() {
        if (teacherGroups.isEmpty()) {
            isEmpty = true
            return
        }
        isLoading = true
        visibleSchedule.clear()
        viewModelScope.launch {
            loadSchedule(selectedGroup.id) {
                visibleSchedule.addAll(groupSchedule.filter { it.dayOfWeek == dayOfWeek })
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