package com.ibashkimi.appinfo.ui.details

import android.content.Context
import android.content.pm.PackageInfo
import android.os.Build
import android.text.format.DateUtils
import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.drawOpacity
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.ripple
import androidx.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
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
                modifier = Modifier.fillMaxSize() + Modifier.wrapContentSize(Alignment.Center) + Modifier.drawOpacity(
                    opacity = 0.5f
                )
            )
        }
        is Result.Success -> {
            PackageInfoLoaded(packageInfo.result)
        }
    }
}

@Composable
private fun PackageInfoLoaded(app: PackageInfo) {
    val context = ContextAmbient.current
    VerticalScroller {
        Column(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                val label = app.applicationInfo.loadLabel(context.packageManager).toString()
                Text(label, style = MaterialTheme.typography.subtitle1)
                Text(app.packageName, style = MaterialTheme.typography.subtitle2)
            }
            Column {
                DetailElem(R.string.installed, app.firstInstallTime.toRelativeTimeSpan())
                DetailElem(R.string.updated, app.firstInstallTime.toRelativeTimeSpan())
                val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    app.longVersionCode.toString()
                } else {
                    @Suppress("DEPRECATION")
                    app.versionCode.toString()
                }
                DetailElem(R.string.version_code, versionCode)
                DetailElem(R.string.version_name, app.versionName.toString())
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    DetailElem(R.string.min_sdk, app.applicationInfo.minSdkVersion.toString())
                }
                DetailElem(R.string.target_sdk, app.applicationInfo.targetSdkVersion.toString())
                DetailElem(R.string.app_uid, app.applicationInfo.uid.toString())
                DetailElem(R.string.component_enabled, app.applicationInfo.enabled.toString())
                DetailElem(
                    R.string.backup_agent_name,
                    app.applicationInfo.backupAgentName ?: stringResource(R.string.none)
                )

                SubsectionItem(R.string.activities, app.activities.sizeToString(context)) {
                    app.activities?.let { navigateTo(Screen.Activities(it)) }
                }
                SubsectionItem(R.string.services, app.services.sizeToString(context)) {
                    app.services?.let { navigateTo(Screen.Services(it)) }
                }
                SubsectionItem(
                    R.string.permissions,
                    app.requestedPermissions.sizeToString(context)
                ) {
                    app.requestedPermissions?.let { navigateTo(Screen.Permissions(app)) }
                }
                SubsectionItem(R.string.providers, app.providers.sizeToString(context)) {
                    app.providers?.let { navigateTo(Screen.Providers(it)) }
                }
                SubsectionItem(R.string.receivers, app.receivers.sizeToString(context)) {
                    app.receivers?.let { navigateTo(Screen.Receivers(it)) }
                }
                SubsectionItem(R.string.features, app.reqFeatures.sizeToString(context)) {
                    app.reqFeatures?.let { navigateTo(Screen.Features(it)) }
                }

                Item(
                    R.string.class_name,
                    app.applicationInfo.className ?: stringResource(R.string.none)
                )
                Item(R.string.data_dir, app.applicationInfo.dataDir)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Item(
                        R.string.device_protected_data_dir,
                        app.applicationInfo.deviceProtectedDataDir
                    )
                }
                Item(R.string.source_dir, app.applicationInfo.sourceDir)
                Item(R.string.public_source_dir, app.applicationInfo.publicSourceDir)
            }
        }
    }
}

@Composable
private fun DetailElem(first: String, second: String) {
    Row(
        modifier = Modifier.fillMaxWidth() + Modifier.padding(16.dp, 8.dp, 16.dp, 8.dp),
        arrangement = Arrangement.SpaceBetween
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
fun SubsectionItem(title: String, summary: String = "", onClick: () -> Unit = {}) {
    Clickable(onClick = onClick, modifier = Modifier.ripple()) {
        Row(
            modifier = Modifier.padding(16.dp) + Modifier.fillMaxWidth(),
            arrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, modifier = Modifier.padding(end = 8.dp))
            Text(text = summary)
        }
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

/*private fun Long.toFormattedDateTime(): String {
    val cal: Calendar = Calendar.getInstance()
    cal.timeInMillis = this
    return DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString()
}*/

private fun Long.toRelativeTimeSpan(): String =
    DateUtils.getRelativeTimeSpanString(
        this,
        System.currentTimeMillis(),
        DateUtils.SECOND_IN_MILLIS
    ).toString()


private fun <T> Array<out T>?.sizeToString(context: Context): String {
    return this?.size?.toString() ?: context.getString(R.string.none)
}
