package com.example.rebenokumnyi

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
import com.example.rebenokumnyi.adminscreens.InfoMode
import com.example.rebenokumnyi.components.URButton
import com.example.rebenokumnyi.components.URListButton
import com.example.rebenokumnyi.components.UROutlineTextField
import com.example.rebenokumnyi.data.InfoTable
import com.example.rebenokumnyi.ui.theme.appTypography
import com.example.rebenokumnyi.viewmodels.InfoViewModel

@Composable
fun ParentInfoScreen(InfoViewModel: InfoViewModel = viewModel()) {
    var mode by remember { mutableStateOf(InfoMode.COMMON) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        val caption= when (mode) {
            InfoMode.TEACHERS -> stringResource(R.string.center_teachers)
            InfoMode.GROUPS -> stringResource(R.string.center_groups)
            else -> stringResource(R.string.info_title)
        }
        Text(text =caption, style = appTypography.titleLarge)
        if (mode == InfoMode.COMMON) {
            Text(text =InfoViewModel.infoAbout, style = appTypography.bodyMedium)
            Spacer(modifier = Modifier.height(5.dp))
            Text(stringResource(R.string.director), style = appTypography.titleLarge)
            Text(text = InfoViewModel.infoDirector)
            Spacer(modifier = Modifier.height(5.dp))
            Text(stringResource(R.string.center_address), style = appTypography.titleLarge)
            Text(InfoViewModel.infoPlace)
            Spacer(modifier = Modifier.height(10.dp))
            URButton(
                text = stringResource(R.string.center_teachers),
                modifier = Modifier.fillMaxWidth()
            ) {
                mode = InfoMode.TEACHERS
            }
            Spacer(modifier = Modifier.height(10.dp))
            URButton(
                text = stringResource(R.string.center_groups),
                modifier = Modifier.fillMaxWidth()
            ) {
                mode = InfoMode.GROUPS
            }
        }
        else if (mode == InfoMode.GROUPS){
            GroupInfoListShow(InfoViewModel.visibleGroups, stringResource(R.string.name), InfoViewModel.isLoading, {mode=InfoMode.COMMON})
        }
        else if (mode == InfoMode.TEACHERS){
            GroupInfoListShow(InfoViewModel.visibleTeachers, stringResource(R.string.fio), InfoViewModel.isLoading, {mode=InfoMode.COMMON})
        }
    }
    DisposableEffect(Unit) {
        InfoViewModel.loadData()
        onDispose {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupInfoListShow(visibleList: List<InfoTable>, nameCaption:String, isLoading: Boolean, back: () -> Unit) {
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
                            .padding(3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text=name, style = appTypography.titleLarge)
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(info)
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