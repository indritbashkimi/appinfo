package com.ibashkimi.appinfo.ui.details

import android.content.pm.ServiceInfo
import androidx.compose.Composable
import androidx.compose.ambient
import androidx.compose.unaryPlus
import androidx.ui.core.ContextAmbient
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import com.ibashkimi.appinfo.R
import com.ibashkimi.appinfo.Screen
import com.ibashkimi.appinfo.navigateTo
import com.ibashkimi.appinfo.ui.Item

@Composable
fun ServicesScreen(services: Array<ServiceInfo>) {
    VerticalScroller {
        Column {
            services.forEach {
                val className = it.name.substringAfterLast(".")
                val classPackage = it.name.substringBeforeLast(".")
                ClickableComponentItem(title = className, summary = classPackage) {
                    navigateTo(Screen.Service(it))
                }
            }
        }
    }
}

@Composable
fun ServiceScreen(service: ServiceInfo) {
    val context = +ambient(ContextAmbient)
    service.apply {
        Column {
            val className = name.substringAfterLast(".")
            val classPackage = name.substringBeforeLast(".")
            Item(R.string.component_name, className)
            Item(R.string.component_package, classPackage)
            Item(R.string.component_permission, permission ?: context.getString(R.string.none))
            Item(R.string.component_process_name, processName)
            Item(R.string.component_enabled, enabled.toString())
            Item(R.string.component_exported, exported.toString())
        }
    }
}