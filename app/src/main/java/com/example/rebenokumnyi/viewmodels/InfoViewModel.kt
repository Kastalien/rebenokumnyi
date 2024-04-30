package com.example.rebenokumnyi.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rebenokumnyi.data.Group
import com.example.rebenokumnyi.data.Roles
import com.example.rebenokumnyi.data.UserRole
import com.example.rebenokumnyi.data.centerInfo
import com.example.rebenokumnyi.data.groups
import com.example.rebenokumnyi.data.loadAllRoles
import com.example.rebenokumnyi.data.loadGroups
import com.example.rebenokumnyi.data.loadInfo
import com.example.rebenokumnyi.data.roles
import kotlinx.coroutines.launch

class InfoViewModel : ViewModel() {
    var visibleGroups = emptyList<Group>().toMutableStateList()
    var visibleTeachers = emptyList<UserRole>().toMutableStateList()
    var isLoading by mutableStateOf(true)
    var infoAbout by mutableStateOf(centerInfo.about)
    var infoDirector by mutableStateOf(centerInfo.director)
    var infoPlace by mutableStateOf(centerInfo.place)

    fun saveInfo(){
        centerInfo.director=infoDirector
        centerInfo.place=infoPlace
        centerInfo.about=infoAbout
        centerInfo.save()
    }

    fun loadData() {
        isLoading=true
        visibleGroups.clear()
        visibleTeachers.clear()
        viewModelScope.launch {
            loadGroups{
                visibleGroups.addAll(groups)
                loadInfo {
                    infoAbout=centerInfo.about
                    infoDirector=centerInfo.director
                    infoPlace= centerInfo.place
                    loadAllRoles{
                        visibleTeachers.addAll(roles.filter { it.role==Roles.TEACHER })
                        isLoading=false
                    }
                }
            }
        }
    }
}