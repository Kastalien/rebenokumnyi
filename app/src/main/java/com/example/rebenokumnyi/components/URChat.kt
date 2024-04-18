package com.example.rebenokumnyi.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compose.RebenokumnyiTheme
import com.example.rebenokumnyi.R
import com.example.rebenokumnyi.data.Chat
import com.example.rebenokumnyi.data.UserRole
import com.example.rebenokumnyi.ui.theme.appTypography
import com.google.android.play.core.integrity.z
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun URChat(
    chat: MutableList<Chat>,
    currentUserId:String,
    isLoading: Boolean,
    isEmpty: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        border = BorderStroke(2.dp, Color.Black),
    ) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(100.dp))
            }
        } else if (isEmpty) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp),
                contentAlignment = Alignment.Center
            ) { Text(text = stringResource(id = R.string.select_opponent)) }
        } else {
            val listState = rememberLazyListState()
            LaunchedEffect(chat.size) {
                listState.animateScrollToItem(chat.size)
            }
            LazyColumn(modifier = Modifier.fillMaxSize().padding(2.dp), state = listState) {
                itemsIndexed(chat) { index, message ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = if (message.user1Id==currentUserId) Arrangement.End else Arrangement.Start
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxSize(0.8F)
                                .padding(2.dp),
                            border = BorderStroke(1.dp, Color.Black),
                            colors = CardDefaults.cardColors(containerColor = if (message.user1Id==currentUserId) Color.Green else Color.White)
                        )
                        {
                            Text(text = message.message, modifier = Modifier.fillMaxWidth().padding(3.dp))
                            Text(
                                text =message.getLocalTime(),
                                modifier = Modifier.fillMaxWidth().padding(3.dp),
                                style = appTypography.displaySmall)
                        }
                    }
                }
            }
        }
    }
}

