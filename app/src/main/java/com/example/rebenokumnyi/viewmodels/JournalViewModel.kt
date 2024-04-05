package com.example.rebenokumnyi.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rebenokumnyi.data.Group
import com.example.rebenokumnyi.data.groups
import com.example.rebenokumnyi.data.loadAllRoles
import com.example.rebenokumnyi.data.loadGroups
import kotlinx.coroutines.launch

class JournalViewModel : ViewModel() {
    var visibleGroups = emptyList<Group>().toMutableStateList()
    var isLoading by mutableStateOf(true)

    fun getGroups() {
        isLoading=true
        visibleGroups.clear()
        viewModelScope.launch {
            loadGroups{
                visibleGroups.addAll(groups.filter { it.id!=0 })
                isLoading=false
            }
            loadAllRoles{}
        }
    }
}