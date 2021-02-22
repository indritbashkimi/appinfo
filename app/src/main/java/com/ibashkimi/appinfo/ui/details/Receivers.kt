package com.ibashkimi.appinfo.ui.details

import android.content.pm.ActivityInfo
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable

@Composable
fun ReceiversScreen(receivers: Array<ActivityInfo>) {
    LazyColumn {
        items(receivers) {
            ReceiverItem(it)
        }
    }
}

@Composable
private fun ReceiverItem(activityInfo: ActivityInfo) {
    val className = activityInfo.name.substringAfterLast(".")
    val classPackage = activityInfo.name.substringBeforeLast(".")
    ComponentItem(title = className, summary = classPackage)
}