package com.example.rebenokumnyi.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rebenokumnyi.data.Roles
import com.example.rebenokumnyi.data.Schedule
import com.example.rebenokumnyi.data.Student
import com.example.rebenokumnyi.data.UserRole
import com.example.rebenokumnyi.data.loadAllRoles
import com.example.rebenokumnyi.data.loadGroups
import com.example.rebenokumnyi.data.loadStudents
import com.example.rebenokumnyi.data.roles
import com.example.rebenokumnyi.data.students
import kotlinx.coroutines.launch

class UsersViewModel : ViewModel() {
    var users = emptyList<UserRole>().toMutableStateList()
    var mode by mutableStateOf(Roles.UNKNOWN)
    var isLoading by mutableStateOf(true)

    fun getStudentsByUser(userId:String):List<Student>{
        return students.filter { it.userId==userId}
    }

    fun getStudents():List<Student>{
        return students
    }

    fun addStudent(name:String, parentId:String){
        var newStudent= Student()
        newStudent.name = name
        newStudent.userId=parentId
        newStudent.addNewStudent()
    }

    fun loadUsers() {
        isLoading=true
        var isRoleLoading=true
        var isStudentLoading=true
        users.clear()
        viewModelScope.launch {
            loadAllRoles{
                users.addAll(roles.filter { it.role==mode })
                isRoleLoading=false
                if (!isStudentLoading)
                    isLoading = false
            }
            loadGroups{}
        }
        viewModelScope.launch {
            loadStudents {
                isStudentLoading=false
                if (!isRoleLoading)
                    isLoading = false
            }
        }
    }
}