package com.ibashkimi.appinfo.ui.details

import android.content.Context
import android.content.pm.PackageInfo
import android.os.Build
import android.text.format.DateUtils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ibashkimi.appinfo.R
import com.ibashkimi.appinfo.Screen
import com.ibashkimi.appinfo.data.Request
import com.ibashkimi.appinfo.data.Result
import com.ibashkimi.appinfo.navigateTo
import com.ibashkimi.appinfo.ui.Item


@Composable
fun DetailsScreen(request: Request<PackageInfo>) {
    when (val packageInfo = request.result) {
        is Result.Loading -> {
            Text(
                text = stringResource(R.string.loading),
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
                    .alpha(0.5f)
            )
        }
        is Result.Success -> {
            PackageInfoLoaded(packageInfo.result)
        }
    }
}

//@OptIn(ExperimentalMaterialApi::class)
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun PackageInfoLoaded(app: PackageInfo) {
    val context = LocalContext.current
    LazyColumn {
        item {
            /*Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {


            }*/
            ListItem(secondaryText = {
                Text(app.packageName, style = MaterialTheme.typography.subtitle2)
            }) {
                val label = app.applicationInfo.loadLabel(context.packageManager).toString()
                Text(label, style = MaterialTheme.typography.subtitle1)
            }
        }
        item { DetailElem(R.string.installed, app.firstInstallTime.toRelativeTimeSpan()) }
        item { DetailElem(R.string.updated, app.firstInstallTime.toRelativeTimeSpan()) }
        val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            app.longVersionCode.toString()
        } else {
            @Suppress("DEPRECATION")
            app.versionCode.toString()
        }
        item { DetailElem(R.string.version_code, versionCode) }
        item { DetailElem(R.string.version_name, app.versionName.toString()) }
        item {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                DetailElem(R.string.min_sdk, app.applicationInfo.minSdkVersion.toString())
            }
        }
        item { DetailElem(R.string.target_sdk, app.applicationInfo.targetSdkVersion.toString()) }
        item { DetailElem(R.string.app_uid, app.applicationInfo.uid.toString()) }
        item { DetailElem(R.string.component_enabled, app.applicationInfo.enabled.toString()) }
        item {
            DetailElem(
                R.string.backup_agent_name,
                app.applicationInfo.backupAgentName ?: stringResource(R.string.none)
            )
        }

        item {
            SubsectionItem(R.string.activities, app.activities.sizeToString(context)) {
                app.activities?.let { navigateTo(Screen.Activities(it)) }
            }
        }
        item {
            SubsectionItem(R.string.services, app.services.sizeToString(context)) {
                app.services?.let { navigateTo(Screen.Services(it)) }
            }
        }
        item {
            SubsectionItem(
                R.string.permissions,
                app.requestedPermissions.sizeToString(context)
            ) {
                app.requestedPermissions?.let { navigateTo(Screen.Permissions(app)) }
            }
        }
        item {
            SubsectionItem(R.string.providers, app.providers.sizeToString(context)) {
                app.providers?.let { navigateTo(Screen.Providers(it)) }
            }
        }
        item {
            SubsectionItem(R.string.receivers, app.receivers.sizeToString(context)) {
                app.receivers?.let { navigateTo(Screen.Receivers(it)) }
            }
        }
        item {
            SubsectionItem(R.string.features, app.reqFeatures.sizeToString(context)) {
                app.reqFeatures?.let { navigateTo(Screen.Features(it)) }
            }
        }

        item {
            Item(
                R.string.class_name,
                app.applicationInfo.className ?: stringResource(R.string.none)
            )
        }
        item {
            Item(R.string.data_dir, app.applicationInfo.dataDir)
        }
        item {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Item(
                    R.string.device_protected_data_dir,
                    app.applicationInfo.deviceProtectedDataDir
                )
            }
        }
        item { Item(R.string.source_dir, app.applicationInfo.sourceDir) }
        item {
            Item(
                R.string.public_source_dir,
                app.applicationInfo.publicSourceDir
            )
        }
    }
}

@Composable
private fun DetailElem(first: String, second: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp, 16.dp, 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            first,
            modifier = Modifier.padding(end = 8.dp),
            style = MaterialTheme.typography.subtitle1
        )
        Text(second, style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun DetailElem(first: Int, second: String) {
    DetailElem(stringResource(first), second)
}

@Composable
fun SubsectionItem(
    title: String,
    summary: String = "",
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, modifier = Modifier.padding(end = 8.dp))
        Text(text = summary)
    }
}

@Composable
fun SubsectionItem(title: Int, summary: String, onClick: () -> Unit = {}) {
    SubsectionItem(stringResource(title), summary, onClick)
}

@Preview
@Composable
fun DetailElemPreview() {
    DetailElem("Installed", "01/01/1979 00:00:00")
}

@Preview
@Composable
fun SubSectionItemPreview() {
    SubsectionItem("Activities", "0")
}

private fun Long.toRelativeTimeSpan(): String =
    DateUtils.getRelativeTimeSpanString(
        this,
        System.currentTimeMillis(),
        DateUtils.SECOND_IN_MILLIS
    ).toString()


private fun <T> Array<out T>?.sizeToString(context: Context): String {
    return this?.size?.toString() ?: context.getString(R.string.none)
}
