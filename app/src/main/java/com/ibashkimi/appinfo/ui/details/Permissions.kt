package com.ibashkimi.appinfo.ui.details

import android.content.pm.PackageInfo
import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.layout.Padding
import androidx.ui.layout.Row

@Composable
fun PermissionsScreen(packageInfo: PackageInfo) {
    VerticalScroller {
        Row {
            Column(modifier = Flexible(1f)) {
                packageInfo.requestedPermissions.sorted().forEach {
                    PermissionItem(it)
                }
            }
        }
    }
}

@Composable
private fun PermissionItem(permission: String) {
    Padding(16.dp, 8.dp, 16.dp, 6.dp) {
        Text(text = permission)
    }
}