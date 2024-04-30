package com.example.rebenokumnyi.adminscreens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Divider
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
import coil.compose.rememberAsyncImagePainter
import com.example.rebenokumnyi.R
import com.example.rebenokumnyi.components.URButton
import com.example.rebenokumnyi.components.URListButton
import com.example.rebenokumnyi.components.UROutlineTextField
import com.example.rebenokumnyi.data.AppData
import com.example.rebenokumnyi.data.InfoTable
import com.example.rebenokumnyi.ui.theme.appTypography
import com.example.rebenokumnyi.viewmodels.InfoViewModel

enum class InfoMode { COMMON, TEACHERS, GROUPS }

private fun launchCamera() {
    Log.d("urlog", "launchCamera")

    // Pick an image from storage
    AppData.cameraIntent.launch(arrayOf("image/*"))
}

@Composable
fun AdminInfoScreen(InfoViewModel: InfoViewModel = viewModel()) {
    var mode by remember { mutableStateOf(InfoMode.COMMON) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        if (mode == InfoMode.COMMON) {
            Text(stringResource(R.string.director))
            UROutlineTextField(
                placeholder = InfoViewModel.infoDirector,
                value = InfoViewModel.infoDirector,
                isSingleLine = true,
                onValueChange = { InfoViewModel.infoDirector = it },
                modifier = Modifier
                    .height(60.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(stringResource(R.string.common_info))
            UROutlineTextField(
                placeholder = InfoViewModel.infoAbout,
                value = InfoViewModel.infoAbout,
                isSingleLine = false,
                onValueChange = { InfoViewModel.infoAbout = it },
                modifier = Modifier
                    .height(270.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(stringResource(R.string.center_address))
            UROutlineTextField(
                placeholder = InfoViewModel.infoPlace,
                value = InfoViewModel.infoPlace,
                isSingleLine = false,
                onValueChange = { InfoViewModel.infoPlace = it },
                modifier = Modifier
                    .height(90.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            URButton(
                text = stringResource(R.string.save_changes),
                modifier = Modifier.fillMaxWidth()
            ) {
                InfoViewModel.saveInfo()
            }
            Spacer(modifier = Modifier.height(30.dp))
            URButton(
                text = stringResource(R.string.input_teacher_information),
                modifier = Modifier.fillMaxWidth()
            ) {
                mode = InfoMode.TEACHERS
            }
            Spacer(modifier = Modifier.height(10.dp))
            URButton(
                text = stringResource(R.string.input_group_information),
                modifier = Modifier.fillMaxWidth()
            ) {
                mode = InfoMode.GROUPS
            }
        }
        else if (mode == InfoMode.GROUPS){
            GroupInfoList(InfoViewModel.visibleGroups, stringResource(R.string.name), InfoViewModel.isLoading, false, {InfoViewModel.loadData()}, {mode=InfoMode.COMMON})
        }
        else if (mode == InfoMode.TEACHERS){
            GroupInfoList(InfoViewModel.visibleTeachers, stringResource(R.string.fio), InfoViewModel.isLoading, true, {InfoViewModel.loadData()}, {mode=InfoMode.COMMON})
        }
    }
    DisposableEffect(Unit) {
        InfoViewModel.loadData()
        onDispose {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupInfoList(visibleList: List<InfoTable>, nameCaption:String, isDataLoading: Boolean, isAddImage: Boolean, onUpdate: () -> Unit, back: () -> Unit) {
    var isLoading by remember { mutableStateOf(isDataLoading) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9F)
            .padding(5.dp),
        border = BorderStroke(2.dp, Color.Black),
    ) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(100.dp))
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(visibleList) { index, infoTable ->
                    var name by remember { mutableStateOf(infoTable.name) }
                    var info by remember { mutableStateOf(infoTable.info) }
                    Row(
                        modifier = Modifier
                            .height(if (isAddImage) 328.dp else 200.dp)
                            .padding(3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(0.8F)
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = nameCaption,
                                style = appTypography.bodyMedium
                            )
                            UROutlineTextField(
                                placeholder = name,
                                value = name,
                                isSingleLine = true,
                                onValueChange = { name = it },
                                modifier = Modifier
                                    .height(60.dp)
                            )
                            Text(
                                text = stringResource(R.string.info),
                                style = appTypography.bodyMedium
                            )
                            UROutlineTextField(
                                placeholder = info,
                                value = info,
                                isSingleLine = false,
                                onValueChange = { info = it },
                                modifier = Modifier
                                    .height(120.dp)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            if (isAddImage) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    if (infoTable.imageLink!="") {
                                        Image(
                                            painter = rememberAsyncImagePainter(infoTable.imageLink),
                                            contentDescription = null,
                                            modifier = Modifier.height(128.dp)
                                        )
                                    }
                                    URListButton(icon = ImageVector.vectorResource(R.drawable.upload)) {
                                        AppData.onStartUpload= {isLoading = true}
                                        AppData.onEndUpload= {
                                            infoTable.addLink(it)
                                            isLoading = false
                                            onUpdate()
                                        }
                                        launchCamera()
                                    }
                                }
                            }
                        }
                        URListButton(icon = ImageVector.vectorResource(R.drawable.save)) {
                            infoTable.setNewInfo(name, info)
                            onUpdate()
                        }
                    }
                Divider(color = Color.Black, thickness = 2.dp)
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}
    URButton(
        text = stringResource(R.string.back),
        modifier = Modifier.fillMaxWidth()
    ) {
        back()
    }
}
