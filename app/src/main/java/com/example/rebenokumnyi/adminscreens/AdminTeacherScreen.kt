package com.example.rebenokumnyi.adminscreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rebenokumnyi.R
import com.example.rebenokumnyi.viewmodels.UsersViewModel
import com.example.rebenokumnyi.components.URListButton
import com.example.rebenokumnyi.components.URSelectGroup
import com.example.rebenokumnyi.data.Roles
import com.example.rebenokumnyi.data.UserRole
import com.example.rebenokumnyi.data.groups
import com.example.rebenokumnyi.ui.theme.appTypography

@Composable
fun AdminTeacherScreen(UsersViewModel: UsersViewModel = viewModel()) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .height(70.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UserTypes(OnClickNewUser = {
                UsersViewModel.mode = Roles.UNKNOWN
                UsersViewModel.getUsers()
            }, OnClickParents = {
                UsersViewModel.mode = Roles.PARENTUSER
                UsersViewModel.getUsers()
            }, OnClickTeachers = {
                UsersViewModel.mode = Roles.TEACHER
                UsersViewModel.getUsers()
            })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .weight(1F)
        ) {
            UserList(UsersViewModel.users, UsersViewModel.mode, UsersViewModel.isLoading ) { UsersViewModel.getUsers() }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (UsersViewModel.mode == Roles.UNKNOWN) {
                Image(painter = painterResource(R.drawable.teacher), contentDescription = "")
                Text("- перенести в учителя")
                Image(painter = painterResource(R.drawable.parents), contentDescription = "")
                Text("- перенести в родители")
            } else if (UsersViewModel.mode == Roles.PARENTUSER) {
                Image(painter = painterResource(R.drawable.group), contentDescription = "")
                Text("- определить в группу")
            }
        }
    }
    DisposableEffect(Unit) {
        UsersViewModel.getUsers()
        onDispose {}
    }
}

@Composable
fun RowScope.UserTypes(
    OnClickNewUser: () -> Unit, OnClickParents: () -> Unit, OnClickTeachers: () -> Unit
) {
    var selectedType by remember { mutableStateOf(0) }
    Row(
        modifier = Modifier.weight(0.7F), verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = (selectedType == 0), onClick = {
            selectedType = 0
            OnClickNewUser()
        })
        Text(
            text = stringResource(R.string.distribute_users),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
    Column(Modifier.weight(0.5F)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = (selectedType == 1), onClick = {
                selectedType = 1
                OnClickTeachers()
            })
            Text(
                text = stringResource(R.string.only_teacher),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = (selectedType == 2), onClick = {
                selectedType = 2
                OnClickParents()
            })
            Text(
                text = stringResource(R.string.only_parents),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserList(users: MutableList<UserRole>, mode: Roles, isLoading:Boolean, onUpdate: () -> Unit) {
    var isSelectGroup by remember { mutableStateOf(false) }
    var userTarget by remember { mutableStateOf(UserRole()) }
    if (isSelectGroup) {
        URSelectGroup(groups) {
            userTarget.setNewGroup(it.id)
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
                    itemsIndexed(users) { index, user ->
                        var edit by remember { mutableStateOf(false) }
                        var userName by remember { mutableStateOf(user.name) }
                        Row(modifier = Modifier
                            .height(if (edit) 60.dp else 50.dp)
                            .padding(3.dp)
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
                                    OutlinedTextField(
                                        value = userName,
                                        onValueChange = { userName = it },
                                        label = { Text(stringResource(R.string.enter_correct_fio)) },
                                        modifier = Modifier.fillMaxSize()
                                    )
                                } else {
                                    Text(text = "${user.name}", style = appTypography.bodyMedium)
                                    if (mode == Roles.PARENTUSER) {
                                        Text(
                                            text = "${user.getGroup()?.name ?: stringResource(R.string.group_not_selected)}",
                                            style = appTypography.displaySmall
                                        )
                                    }
                                }
                            }
                            if (mode == Roles.UNKNOWN) {
                                URListButton(icon = ImageVector.vectorResource(R.drawable.teacher)) {
                                    user.setNewRole(Roles.TEACHER)
                                    onUpdate()
                                }
                                URListButton(icon = ImageVector.vectorResource(R.drawable.parents)) {
                                    user.setNewRole(Roles.PARENTUSER)
                                    onUpdate()
                                }
                                URListButton(icon = ImageVector.vectorResource(R.drawable.delete)) {
                                    user.remove()
                                    onUpdate()
                                }
                            } else {
                                if (edit) {
                                    URListButton(icon = ImageVector.vectorResource(R.drawable.save)) {
                                        user.setNewName(userName)
                                        edit = false
                                        onUpdate()
                                    }
                                } else {
                                    URListButton(icon = ImageVector.vectorResource(R.drawable.delete)) {
                                        user.remove()
                                        onUpdate()
                                    }
                                    URListButton(icon = ImageVector.vectorResource(R.drawable.edit)) {
                                        edit = true
                                    }
                                }
                            }
                            if ((mode == Roles.PARENTUSER) && (!edit)) URListButton(
                                icon = ImageVector.vectorResource(
                                    R.drawable.group
                                )
                            ) {
                                isSelectGroup = true
                                userTarget = user
                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
            }
        }
    }
}
