package com.ibashkimi.appinfo.ui.details

import android.content.Context
import android.content.pm.PackageInfo
import android.os.Build
import android.text.format.DateUtils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ibashkimi.appinfo.R
import com.ibashkimi.appinfo.ui.component.Item
import com.ibashkimi.appinfo.ui.component.BackNavigationIcon

@Composable
fun DetailsScreen(
    navigateToActivities: (String) -> Unit,
    navigateToServices: (String) -> Unit,
    navigateToPermissions: (String) -> Unit,
    navigateToFeatures: (String) -> Unit,
    navigateToProviders: (String) -> Unit,
    navigateToReceivers: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = { Text(stringResource(id = R.string.details)) },
                navigationIcon = {
                    BackNavigationIcon()
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = {
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            when (val result = uiState.value) {
                null -> {
                    Text(
                        text = stringResource(R.string.loading),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                            .alpha(0.5f)
                            .padding(it)
                    )
                }
                else -> {
                    PackageInfoLoaded(
                        result,
                        navigateToActivities = navigateToActivities,
                        navigateToServices = navigateToServices,
                        navigateToPermissions = navigateToPermissions,
                        navigateToFeatures = navigateToFeatures,
                        navigateToProviders = navigateToProviders,
                        navigateToReceivers = navigateToReceivers,
                        modifier = Modifier.padding(it)
                    )
                }
            }
        }
    )
}

@Composable
private fun PackageInfoLoaded(
    packageInfo: PackageInfo,
    navigateToActivities: (String) -> Unit,
    navigateToServices: (String) -> Unit,
    navigateToPermissions: (String) -> Unit,
    navigateToFeatures: (String) -> Unit,
    navigateToProviders: (String) -> Unit,
    navigateToReceivers: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(modifier.verticalScroll(rememberScrollState())) {
        ListItem(
            headlineText = {
                val label = remember(packageInfo.packageName) {
                    packageInfo.applicationInfo.loadLabel(context.packageManager).toString()
                }
                Text(label)
            },
            supportingText = {
                Text(packageInfo.packageName)
            })
        DetailElem(
            R.string.installed,
            remember(packageInfo.firstInstallTime) { packageInfo.firstInstallTime.toRelativeTimeSpan() })
        DetailElem(
            R.string.updated,
            remember(packageInfo.firstInstallTime) { packageInfo.firstInstallTime.toRelativeTimeSpan() })
        val versionCode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo.longVersionCode.toString()
        } else {
            @Suppress("DEPRECATION")
            packageInfo.versionCode.toString()
        }
        DetailElem(R.string.version_code, versionCode)
        DetailElem(R.string.version_name, packageInfo.versionName.toString())
        DetailElem(R.string.min_sdk, packageInfo.applicationInfo.minSdkVersion.toString())
        DetailElem(R.string.target_sdk, packageInfo.applicationInfo.targetSdkVersion.toString())
        DetailElem(R.string.app_uid, packageInfo.applicationInfo.uid.toString())
        DetailElem(R.string.component_enabled, packageInfo.applicationInfo.enabled.toString())
        Card(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                "Components",
                Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.surfaceTint,
                fontWeight = FontWeight.Medium,
            )
            SubsectionItem(R.string.activities, packageInfo.activities.sizeToString(context)) {
                packageInfo.activities?.let { navigateToActivities(packageInfo.packageName) }
            }
            SubsectionItem(R.string.services, packageInfo.services.sizeToString(context)) {
                packageInfo.services?.let { navigateToServices(packageInfo.packageName) }
            }
            SubsectionItem(
                R.string.permissions,
                packageInfo.requestedPermissions.sizeToString(context)
            ) {
                packageInfo.requestedPermissions?.let { navigateToPermissions(packageInfo.packageName) }
            }
            SubsectionItem(R.string.providers, packageInfo.providers.sizeToString(context)) {
                packageInfo.providers?.let { navigateToProviders(packageInfo.packageName) }
            }
            SubsectionItem(R.string.receivers, packageInfo.receivers.sizeToString(context)) {
                packageInfo.receivers?.let { navigateToReceivers(packageInfo.packageName) }
            }
            SubsectionItem(R.string.features, packageInfo.reqFeatures.sizeToString(context)) {
                packageInfo.reqFeatures?.let { navigateToFeatures(packageInfo.packageName) }
            }
        }
        Text(
            "Data",
            Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Card(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Item(
                R.string.class_name,
                packageInfo.applicationInfo.className ?: stringResource(R.string.none)
            )
            Item(
                R.string.backup_agent_name,
                packageInfo.applicationInfo.backupAgentName ?: stringResource(R.string.none)
            )
            Item(R.string.data_dir, packageInfo.applicationInfo.dataDir)
            Item(
                R.string.device_protected_data_dir,
                packageInfo.applicationInfo.deviceProtectedDataDir
            )
            Item(R.string.source_dir, packageInfo.applicationInfo.sourceDir)
            Item(
                R.string.public_source_dir,
                packageInfo.applicationInfo.publicSourceDir
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
            style = MaterialTheme.typography.titleSmall
        )
        Text(second, style = MaterialTheme.typography.titleMedium)
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
fun SubsectionItem(
    title: Int,
    summary: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    ListItem(
        headlineText = { Text(stringResource(title)) },
        modifier = modifier.clickable(onClick = onClick),
        trailingContent = { Text(summary) },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent)
    )
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
