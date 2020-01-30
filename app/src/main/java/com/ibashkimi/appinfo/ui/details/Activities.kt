package com.ibashkimi.appinfo.ui.details

import android.content.pm.ActivityInfo
import androidx.compose.Composable
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.res.stringResource
import com.ibashkimi.appinfo.R
import com.ibashkimi.appinfo.Screen
import com.ibashkimi.appinfo.navigateTo
import com.ibashkimi.appinfo.ui.Item

@Composable
fun ActivitiesScreen(activities: Array<ActivityInfo>) {
    VerticalScroller {
        Column {
            activities.forEach {
                val className = it.name.substringAfterLast(".")
                val classPackage = it.name.substringBeforeLast(".")
                ClickableComponentItem(title = className, summary = classPackage) {
                    navigateTo(Screen.Activity(it))
                }
            }
        }
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
