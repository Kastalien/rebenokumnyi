package com.example.rebenokumnyi.viewmodels

import android.telecom.Call
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rebenokumnyi.data.Group
import com.example.rebenokumnyi.data.Roles
import com.example.rebenokumnyi.data.UserRole
import com.example.rebenokumnyi.data.loadAllRoles
import com.example.rebenokumnyi.data.loadGroups
import com.example.rebenokumnyi.data.roles
import com.google.android.gms.common.api.Response
import kotlinx.coroutines.launch

class UsersViewModel : ViewModel() {
    var users = emptyList<UserRole>().toMutableStateList()
    var mode by mutableStateOf(Roles.UNKNOWN)
    var isLoading by mutableStateOf(true)

    fun getUsers() {
        isLoading=true
        users.clear()
        viewModelScope.launch {
            loadAllRoles{
                users.addAll(roles.filter { it.role==mode })
                isLoading=false
            }
            loadGroups{}
        }
    }
}