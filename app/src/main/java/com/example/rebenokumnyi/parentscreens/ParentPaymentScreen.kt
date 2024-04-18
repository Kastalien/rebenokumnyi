package com.example.rebenokumnyi.parentscreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.rebenokumnyi.R
import com.example.rebenokumnyi.ui.theme.appTypography

@Composable
fun ParentPaymentScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(3.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.make_payment_by_qr), style = appTypography.titleLarge)
        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(id = R.drawable.qrcode),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth(0.7F)
                .padding(2.dp)
        )
    }
}