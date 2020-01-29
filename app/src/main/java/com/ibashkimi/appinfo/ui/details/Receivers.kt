package com.ibashkimi.appinfo.ui.details

import android.content.pm.ActivityInfo
import androidx.compose.Composable
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column

@Composable
fun ReceiversScreen(receivers: Array<ActivityInfo>) {
    VerticalScroller {
        Column {
            receivers.forEach {
                ReceiverItem(it)
            }
        }
    }
}

@Composable
private fun ReceiverItem(activityInfo: ActivityInfo) {
    val className = activityInfo.name.substringAfterLast(".")
    val classPackage = activityInfo.name.substringBeforeLast(".")
    ComponentItem(title = className, summary = classPackage)
}