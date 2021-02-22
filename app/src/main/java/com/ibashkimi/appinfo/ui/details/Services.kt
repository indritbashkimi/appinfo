package com.ibashkimi.appinfo.ui.details

import android.content.pm.ServiceInfo
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
fun ServicesScreen(services: Array<ServiceInfo>) {
    LazyColumn {
        items(services) {
            ServiceItem(
                service = it,
                modifier = Modifier.clickable { navigateTo(Screen.Service(it)) })
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ServiceItem(service: ServiceInfo, modifier: Modifier) {
    ListItem(modifier, secondaryText = { Text(service.name.substringBeforeLast(".")) }) {
        Text(service.name.substringAfterLast("."))
    }
}

@Composable
fun ServiceScreen(service: ServiceInfo) {
    service.apply {
        Column {
            val className = name.substringAfterLast(".")
            val classPackage = name.substringBeforeLast(".")
            Item(R.string.component_name, className)
            Item(R.string.component_package, classPackage)
            Item(R.string.component_permission, permission ?: stringResource(R.string.none))
            Item(R.string.component_process_name, processName)
            Item(R.string.component_enabled, enabled.toString())
            Item(R.string.component_exported, exported.toString())
        }
    }
}