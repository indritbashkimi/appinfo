package com.ibashkimi.appinfo.ui.details

import android.content.pm.PackageInfo
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.*
import androidx.ui.unit.dp

@Composable
fun PermissionsScreen(packageInfo: PackageInfo) {
    VerticalScroller {
        Column(modifier = Modifier.fillMaxWidth()) {
            packageInfo.requestedPermissions.sorted().forEach {
                PermissionItem(it)
            }
        }
    }
}

@Composable
private fun PermissionItem(permission: String) {
    Text(text = permission, modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 8.dp))
}