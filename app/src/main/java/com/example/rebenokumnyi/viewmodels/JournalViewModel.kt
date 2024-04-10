package com.example.rebenokumnyi.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rebenokumnyi.data.Group
import com.example.rebenokumnyi.data.Journal
import com.example.rebenokumnyi.data.Schedule
import com.example.rebenokumnyi.data.Student
import com.example.rebenokumnyi.data.currentRole
import com.example.rebenokumnyi.data.groupSchedule
import com.example.rebenokumnyi.data.groups
import com.example.rebenokumnyi.data.loadSchedule
import com.example.rebenokumnyi.data.loadSelectedJournal
import com.example.rebenokumnyi.data.loadStudents
import com.example.rebenokumnyi.data.loadSubjects
import com.example.rebenokumnyi.data.students
import com.example.rebenokumnyi.data.subjects
import kotlinx.coroutines.launch
import java.time.LocalDate

class JournalViewModel : ViewModel() {
    val teacherGroups = groups.filter { it.userId == currentRole.userId }
    var groupStudents = listOf<Student>()
    var visibleSchedule = emptyList<Schedule>().toMutableStateList()
    var isLoading by mutableStateOf(false)
    var isEmpty by mutableStateOf(false)
    var selectedGroup by mutableStateOf(Group())
    var selectedStudent by mutableStateOf(Student())
    var currentDate: LocalDate by mutableStateOf(LocalDate.now())

    fun loadJournal() {
        if (teacherGroups.isEmpty()) {
            isEmpty = true
            return
        }
        isLoading = true
        var loadingCount = 0
        if (subjects.isEmpty()) loadingCount++
        if (selectedGroup.id != -1) loadingCount += 2
        if (selectedStudent.id != "") loadingCount ++
        if (loadingCount == 0) {
            isLoading = false
            return
        }
        visibleSchedule.clear()
        if (selectedGroup.id != -1) {
            viewModelScope.launch {
                loadSchedule(selectedGroup.id) {
                    visibleSchedule.addAll(groupSchedule.filter { it.dayOfWeek == currentDate.dayOfWeek.value }
                        .sortedBy { it.start })
                    loadingCount--
                    if (loadingCount == 0) isLoading = false
                }
            }
            viewModelScope.launch {
                loadStudents(selectedGroup.id) {
                    groupStudents = students
                    loadingCount--
                    if (loadingCount == 0) isLoading = false
                }
            }
        }
        if (subjects.isEmpty()) {
            viewModelScope.launch {
                loadSubjects {
                    loadingCount--
                    if (loadingCount == 0) isLoading = false
                }
            }
        }
        if (selectedStudent.id != "") {
            viewModelScope.launch {
                loadSelectedJournal(selectedStudent.id, currentDate.toString()) {
                    loadingCount--
                    if (loadingCount == 0) isLoading = false
                }
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

    fun saveNote(journalId: String?, scheduleId: String, newNote: String) {
        var newJournal = Journal()
        newJournal.date = currentDate.toString()
        newJournal.scheduleId = scheduleId
        newJournal.studentId = selectedStudent.id
        newJournal.note = newNote
        if (journalId == null) {
            newJournal.addNewJournal()
        } else {
            newJournal.id = journalId
            newJournal.save()
        }
    }
}