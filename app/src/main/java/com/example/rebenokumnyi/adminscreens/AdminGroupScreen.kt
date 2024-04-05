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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import com.example.rebenokumnyi.viewmodels.GroupViewModel
import com.example.rebenokumnyi.components.URListButton
import com.example.rebenokumnyi.components.URSelectTeacher
import com.example.rebenokumnyi.data.Group
import com.example.rebenokumnyi.data.Roles
import com.example.rebenokumnyi.data.groups
import com.example.rebenokumnyi.data.roles
import com.example.rebenokumnyi.ui.theme.appTypography
import com.example.rebenokumnyi.ui.theme.md_theme_light_secondaryContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminGroupScreen(GroupViewModel: GroupViewModel = viewModel()) {
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
            GroupList(
                GroupViewModel.visibleGroups,
                GroupViewModel.isLoading
            ) { GroupViewModel.getGroups() }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .height(130.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                var newGroupName by remember { mutableStateOf("") }
                Button(
                    onClick = {
                        val newGroup=Group(groups.count()+1,newGroupName)
                        newGroup.save()
                        GroupViewModel.getGroups()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Text(stringResource(R.string.add_group))
                }
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = newGroupName,
                    onValueChange = { newGroupName = it },
                    label = { Text(stringResource(R.string.enter_new_group_name)) },
                    colors=TextFieldDefaults.textFieldColors(containerColor = md_theme_light_secondaryContainer),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                )
            }
        }
    }
    DisposableEffect(Unit) {
        GroupViewModel.getGroups()
        onDispose {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupList(visibleGroups: MutableList<Group>, isLoading: Boolean, onUpdate: () -> Unit) {
    var isSelectTeacher by remember { mutableStateOf(false) }
    var groupTarget by remember { mutableStateOf(Group()) }
    if (isSelectTeacher) {
        URSelectTeacher(roles.filter { it.role == Roles.TEACHER }) {
            groupTarget.setNewTeacher(it)
            isSelectTeacher = false
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
                    itemsIndexed(visibleGroups) { index, group ->
                        var edit by remember { mutableStateOf(false) }
                        var groupName by remember { mutableStateOf(group.name) }
                        Row(
                            modifier = Modifier
                                .height(if (edit) 70.dp else 60.dp)
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
                                        value = groupName,
                                        onValueChange = { groupName = it },
                                        label = { Text(stringResource(R.string.enter_new_group_name)) },
                                        modifier = Modifier.fillMaxSize()
                                    )
                                } else {
                                    Text(text = "${group.name}", style = appTypography.bodyMedium)
                                    Text(
                                        text = "${group.getTeacher()?.name ?: stringResource(R.string.teacher_not_selected)}",
                                        style = appTypography.displaySmall
                                    )
                                }
                            }
                            if (edit) {
                                URListButton(icon = ImageVector.vectorResource(R.drawable.save)) {
                                    group.setNewName(groupName)
                                    edit = false
                                    onUpdate()
                                }
                            }
                            else {
                                URListButton(icon = ImageVector.vectorResource(R.drawable.teacher)) {
                                    isSelectTeacher = true
                                    groupTarget = group
                                    onUpdate()
                                }
                                URListButton(icon = ImageVector.vectorResource(R.drawable.edit)) {
                                    edit = true
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