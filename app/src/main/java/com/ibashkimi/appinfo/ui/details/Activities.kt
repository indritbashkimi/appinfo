package com.ibashkimi.appinfo.ui.details

import android.content.pm.ActivityInfo
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ibashkimi.appinfo.R
import com.ibashkimi.appinfo.Screen
import com.ibashkimi.appinfo.navigateTo
import com.ibashkimi.appinfo.ui.Item

@Composable
fun ActivitiesScreen(activities: Array<ActivityInfo>) {
    LazyColumn {
        items(activities) {
            ActivityItem(it, Modifier.clickable { navigateTo(Screen.Activity(it)) })
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ActivityItem(activity: ActivityInfo, modifier: Modifier = Modifier) {
    ListItem(modifier, secondaryText = { Text(activity.name.substringBeforeLast(".")) }) {
        Text(activity.name.substringAfterLast("."))
    }
}

@Composable
fun ActivityScreen(activity: ActivityInfo) {
    Column {
        val className = activity.name.substringAfterLast(".")
        val classPackage = activity.name.substringBeforeLast(".")
        Item(R.string.component_name, className)
        Item(R.string.component_package, classPackage)
        Item(R.string.target_activity, activity.targetActivity ?: stringResource(R.string.none))
        Item(R.string.task_affinity, activity.taskAffinity ?: stringResource(R.string.none))
        Item(R.string.component_permission, activity.permission ?: stringResource(R.string.none))
        Item(R.string.component_process_name, activity.processName)
        Item(R.string.component_enabled, activity.enabled.toString())
        Item(R.string.component_exported, activity.exported.toString())
    }
}
