package com.example.rebenokumnyi.teacherscreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rebenokumnyi.R
import com.example.rebenokumnyi.components.URButton
import com.example.rebenokumnyi.components.URChat
import com.example.rebenokumnyi.components.URGroupSelector
import com.example.rebenokumnyi.components.UROutlineTextField
import com.example.rebenokumnyi.components.URStudentSelector
import com.example.rebenokumnyi.data.AppData
import com.example.rebenokumnyi.data.Student
import com.example.rebenokumnyi.data.UserRole
import com.example.rebenokumnyi.viewmodels.ChatViewModel
import com.example.rebenokumnyi.viewmodels.JournalViewModel

@Composable
fun TeacherChatScreen(ChatViewModel: ChatViewModel = viewModel()) {
    var newMessage by remember { mutableStateOf("") }
    var isSelectGroup by remember { mutableStateOf(false) }
    var isSelectStudent by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(3.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isSelectGroup) {
            URGroupSelector(ChatViewModel.teacherGroups) {
                ChatViewModel.selectedGroup = it
                ChatViewModel.selectedUser = UserRole()
                ChatViewModel.getStudents()
                isSelectGroup = false
            }
        } else if (isSelectStudent) {
            URStudentSelector(ChatViewModel.groupStudents) {
                ChatViewModel.selectedUser = it.getParent()?: UserRole()
                isSelectStudent = false
                ChatViewModel.loadData()
            }
        } else {
            Text(stringResource(id = R.string.aGroup))
            Button(
                onClick = {
                    isSelectGroup = true
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            ) {
                Text(ChatViewModel.selectedGroup.name)
            }
            Text(stringResource(id = R.string.aParentStudent))
            Button(
                onClick = {
                    isSelectStudent = true
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            ) {
                Text(ChatViewModel.selectedUser.name)
            }
            Spacer(modifier = Modifier.height(5.dp))
            URChat(
                chat = ChatViewModel.visibleChat,
                currentUserId = AppData.getUserID(),
                isLoading = ChatViewModel.isLoading,
                isEmpty = ChatViewModel.isEmpty,
                modifier = Modifier.weight(1F)
            )
            Spacer(modifier = Modifier.height(5.dp))
            URButton(
                text = stringResource(id = R.string.enter_message),
                modifier = Modifier
                    .height(30.dp)
                    .fillMaxWidth()
            ) {
                if (newMessage != "") {
                    ChatViewModel.saveMessage(newMessage)
                    newMessage = ""
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            UROutlineTextField(
                placeholder = stringResource(id = R.string.enter_message),
                value = newMessage,
                isSingleLine = false,
                onValueChange = { newMessage = it },
                modifier = Modifier.height(60.dp)
            )
            Spacer(modifier = Modifier.height(3.dp))
        }
    }
}
