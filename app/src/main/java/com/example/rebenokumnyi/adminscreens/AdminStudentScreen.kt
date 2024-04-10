package com.example.rebenokumnyi.adminscreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rebenokumnyi.R
import com.example.rebenokumnyi.components.URButton
import com.example.rebenokumnyi.components.URListButton
import com.example.rebenokumnyi.components.UROutlineTextField
import com.example.rebenokumnyi.components.URGroupSelector
import com.example.rebenokumnyi.data.Student
import com.example.rebenokumnyi.data.groups
import com.example.rebenokumnyi.ui.theme.appTypography
import com.example.rebenokumnyi.viewmodels.UsersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminStudentScreen(parentId: String, UsersViewModel: UsersViewModel = viewModel(), onBack:()->Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .weight(1F)
        ) {
            StudentList(
                UsersViewModel.getStudentsByUser(parentId), UsersViewModel.isLoading
            ) { UsersViewModel.loadUsers() }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.SpaceEvenly
            ) {
                var newStudentName by remember { mutableStateOf("") }
                URButton(
                    text = stringResource(R.string.add_students),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                ) {
                    if (newStudentName!="") {
                        UsersViewModel.addStudent(newStudentName, parentId)
                        UsersViewModel.loadUsers()
                        newStudentName = ""
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                UROutlineTextField(
                    placeholder = stringResource(R.string.enter_new_group_name),
                    value = newStudentName,
                    onValueChange ={ newStudentName = it })
                Spacer(modifier = Modifier.height(10.dp))
                URButton(
                    text = stringResource(R.string.back),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                ) {
                    onBack()
                }
            }
        }
    }
    DisposableEffect(Unit) {
        UsersViewModel.loadUsers()
        onDispose {}
    }
}


@Composable
fun StudentList(students: List<Student>, isLoading: Boolean, onUpdate: () -> Unit) {
    var isSelectGroup by remember { mutableStateOf(false) }
    var studentTarget by remember { mutableStateOf(Student()) }
    if (isSelectGroup) {
        URGroupSelector(groups) {
            studentTarget.setNewGroup(it.id)
            isSelectGroup = false
            onUpdate()
        }
    } else {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            border = BorderStroke(2.dp, Color.Black),
        ) {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(100.dp))
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    itemsIndexed(students) { index, student ->
                        var edit by remember { mutableStateOf(false) }
                        var studentName by remember { mutableStateOf(student.name) }
                        Row(
                            modifier = Modifier
                                .height(if (edit) 70.dp else 45.dp)
                                .padding(3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(0.11F)
                                    .fillMaxHeight(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "${index + 1}.", style = appTypography.bodyMedium)
                            }
                            Column(
                                modifier = Modifier
                                    .weight(0.9F)
                                    .fillMaxHeight(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                if (edit) {
                                    UROutlineTextField(
                                        value = studentName,
                                        onValueChange = { studentName = it },
                                        placeholder = stringResource(R.string.enter_correct_fio),
                                    )
                                } else {
                                    Text(text = "${student.name}", style = appTypography.bodyMedium)
                                    Text(
                                        text = student.getGroup()?.name
                                            ?: stringResource(R.string.group_not_selected),
                                        style = appTypography.displaySmall
                                    )
                                }
                            }
                            if (edit) {
                                URListButton(icon = ImageVector.vectorResource(R.drawable.save)) {
                                    student.setNewName(studentName)
                                    edit = false
                                    onUpdate()
                                }
                            } else {
                                URListButton(icon = ImageVector.vectorResource(R.drawable.group)) {
                                    isSelectGroup = true
                                    studentTarget = student
                                    onUpdate()
                                }
                                URListButton(icon = ImageVector.vectorResource(R.drawable.edit)) {
                                    edit = true
                                }
                                URListButton(icon = ImageVector.vectorResource(R.drawable.delete)) {
                                    student.remove()
                                    onUpdate()
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }
        }
    }
}