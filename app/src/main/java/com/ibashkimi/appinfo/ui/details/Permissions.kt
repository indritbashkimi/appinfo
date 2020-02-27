package com.ibashkimi.appinfo.ui.details

import android.content.pm.PackageInfo
import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.layout.LayoutPadding
import androidx.ui.layout.LayoutWidth
import androidx.ui.unit.dp

@Composable
fun PermissionsScreen(packageInfo: PackageInfo) {
    VerticalScroller {
        Column(modifier = LayoutWidth.Fill) {
            packageInfo.requestedPermissions.sorted().forEach {
                PermissionItem(it)
            }
        }
    }
}

@Composable
private fun PermissionItem(permission: String) {
    Text(text = permission, modifier = LayoutPadding(16.dp, 8.dp, 16.dp, 8.dp))
}