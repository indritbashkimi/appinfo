package com.ibashkimi.appinfo.ui.details

import android.content.pm.PackageInfo
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PermissionsScreen(packageInfo: PackageInfo) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(packageInfo.requestedPermissions.sorted()) {
            PermissionItem(it)
        }
    }
}

@Composable
private fun PermissionItem(permission: String) {
    Text(text = permission, modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 8.dp))
}