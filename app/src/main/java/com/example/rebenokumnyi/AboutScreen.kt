package com.example.rebenokumnyi

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.rebenokumnyi.ui.theme.appTypography

@Composable
fun AboutScreen(
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(3.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(0.15F)
                .padding(3.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.ministry), style = appTypography.bodyMedium)
            Text(stringResource(R.string.Univercity), style = appTypography.bodyMedium)
            Spacer(modifier = Modifier.height(15.dp))
            Text(stringResource(R.string.faculty), style = appTypography.bodyMedium)
            Text(stringResource(R.string.department), style = appTypography.bodyMedium)
            Spacer(modifier = Modifier.height(15.dp))
            Text(stringResource(R.string.speciality), style = appTypography.bodyMedium)
        }
        Column(
            modifier = Modifier
                .weight(0.5F)
                .padding(3.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(stringResource(R.string.diploma), style = appTypography.titleLarge)
            Spacer(modifier = Modifier.height(10.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier
                    .size(250.dp)
                    .padding(2.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(stringResource(R.string.mobile_application), style = appTypography.titleLarge)
            Text(stringResource(R.string.educational_center), style = appTypography.titleLarge)
            Spacer(modifier = Modifier.height(10.dp))
            Text(stringResource(R.string.author), style = appTypography.titleMedium)
            Text(stringResource(R.string.author_name), style = appTypography.titleMedium)
            Text(stringResource(R.string.author_group))
            Spacer(modifier = Modifier.height(10.dp))
        }
        Column(
            modifier = Modifier
                .weight(0.1F)
                .padding(3.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.native_city), style = appTypography.bodyMedium)
        }
    }
}