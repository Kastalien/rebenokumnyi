package com.example.rebenokumnyi.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.rebenokumnyi.data.AppData
import com.example.rebenokumnyi.data.Chat
import com.example.rebenokumnyi.data.Group
import com.example.rebenokumnyi.data.Roles
import com.example.rebenokumnyi.data.Student
import com.example.rebenokumnyi.data.UserRole
import com.example.rebenokumnyi.data.groups
import com.example.rebenokumnyi.data.selectedChat
import com.example.rebenokumnyi.data.students
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import java.time.LocalDateTime
import java.time.ZoneOffset

class ChatViewModel : ViewModel() {
    var visibleGroups = emptyList<Group>().toMutableStateList()
    var selectedGroup by mutableStateOf(Group())
    var selectedUser by mutableStateOf(UserRole())
    var isLoading by mutableStateOf(false)
    var isEmpty by mutableStateOf(false)
    var visibleChat = emptyList<Chat>().toMutableStateList()
    var teacherGroups=groups.filter {it.userId==AppData.getUserID()}.toMutableStateList()
    var groupStudents=emptyList<Student>().toMutableStateList()

    fun loadData() {
        visibleGroups.clear()
        visibleChat.clear()
        if (AppData.getRole() == Roles.TEACHER)
            visibleGroups.addAll(groups.filter { it.userId == AppData.getUserID() })
        else
            selectedUser = AppData.currentChild.getGroup()?.getTeacher() ?: UserRole()
        if (selectedUser.userId != "") {
            isLoading = true
            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                     selectedChat =
                         dataSnapshot
                             .getValue<Map<String, Chat>>()
                             ?.map{ it.value }
                             ?.toList()
                             ?.filter{ ((it.user1Id == AppData.getUserID())&&(it.user2Id == selectedUser.userId))||((it.user2Id == AppData.getUserID())&&(it.user1Id == selectedUser.userId))}
                             ?:listOf()
                    visibleChat.clear()
                    visibleChat.addAll(selectedChat.sortedBy { it.dateTime })
                    isLoading = false
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w("urlog", "loadPost:onCancelled", databaseError.toException())
                    isLoading = false
                }
            }
            AppData.database.child("chat").addValueEventListener(postListener)
        }
    }

    fun saveMessage(message: String) {
        var newMessage = Chat()
        newMessage.dateTime = LocalDateTime.now(ZoneOffset.UTC).toString()
        newMessage.user1Id = AppData.getUserID()
        newMessage.user2Id = selectedUser.userId
        newMessage.message = message
        newMessage.addNewMessage()
    }

    fun getStudents() {
        groupStudents= students.filter { it.groupId==selectedGroup.id }.toMutableStateList()
    }

}