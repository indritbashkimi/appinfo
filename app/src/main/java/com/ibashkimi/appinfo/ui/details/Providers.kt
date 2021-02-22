package com.ibashkimi.appinfo.ui.details

import android.content.pm.ProviderInfo
import android.os.Build
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
fun ProvidersScreen(providers: Array<ProviderInfo>) {
    LazyColumn {
        items(providers) {
            ProviderItem(it, Modifier.clickable { navigateTo(Screen.Provider(it)) })
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ProviderItem(provider: ProviderInfo, modifier: Modifier = Modifier) {
    ListItem(modifier, secondaryText = { Text(provider.name.substringBeforeLast(".")) }) {
        Text(provider.name.substringAfterLast("."))
    }
}

@Composable
fun ProviderScreen(provider: ProviderInfo) {
    Column {
        provider.apply {
            Item(R.string.component_name, name)
            Item(R.string.component_package, packageName)
            Item(R.string.component_process_name, processName)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Item(R.string.component_split_name, splitName ?: stringResource(R.string.none))
            }
            Item(R.string.component_enabled, enabled.toString())
            Item(R.string.component_exported, exported.toString())
            Item(
                R.string.provider_read_permission,
                readPermission ?: stringResource(R.string.none)
            )
            Item(
                R.string.provider_write_permission,
                writePermission ?: stringResource(R.string.none)
            )
            Item(
                R.string.component_multiprocess,
                stringResource(if (multiprocess) R.string.multiprocess_true else R.string.multiprocess_false)
            )
            Item(R.string.provider_grant_uri_permissions, grantUriPermissions.toString())
            Item(
                R.string.provider_uri_permission_patterns,
                uriPermissionPatterns?.contentToString() ?: stringResource(R.string.none)
            )
            Item(
                R.string.provider_path_permissions,
                pathPermissions?.contentToString() ?: stringResource(R.string.none)
            )
        }
    }
}