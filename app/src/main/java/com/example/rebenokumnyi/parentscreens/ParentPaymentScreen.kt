package com.example.rebenokumnyi.parentscreens

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
import androidx.compose.ui.unit.dp
import com.example.rebenokumnyi.R

@Composable
fun ParentPaymentScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(3.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Отсканируйте Qr-код и внесите оплату")
        Spacer(modifier = Modifier.height(10.dp))
        Image(
            painter = painterResource(id = R.drawable.qrcode),
            contentDescription = "",

            modifier = Modifier
                .size(200.dp)
                .padding(2.dp)
        )
    }
}