package com.ibashkimi.appinfo.ui.details

import android.content.pm.ActivityInfo
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ReceiversScreen(receivers: Array<ActivityInfo>) {
    LazyColumn {
        items(receivers) {
            ReceiverItem(it)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ReceiverItem(activityInfo: ActivityInfo, modifier: Modifier = Modifier) {
    ListItem(modifier, secondaryText = { Text(activityInfo.name.substringBeforeLast(".")) }) {
        Text(activityInfo.name.substringAfterLast("."))
    }
}