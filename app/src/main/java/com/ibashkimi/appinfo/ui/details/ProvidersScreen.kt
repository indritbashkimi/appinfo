package com.ibashkimi.appinfo.ui.details

import android.content.pm.ProviderInfo
import android.os.Build
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ibashkimi.appinfo.R
import com.ibashkimi.appinfo.ui.component.Item
import com.ibashkimi.appinfo.ui.component.BackNavigationIcon
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun ProvidersScreen(modifier: Modifier = Modifier, viewModel: DetailsViewModel = hiltViewModel()) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = { Text(stringResource(id = R.string.providers)) },
                navigationIcon = {
                    BackNavigationIcon()
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = {
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            val providers: ImmutableList<ProviderInfo> = remember(uiState.value) {
                uiState.value?.providers?.asList()?.toImmutableList() ?: persistentListOf()
            }
            val providerDetails = remember { mutableStateOf<ProviderInfo?>(null) }
            LazyColumn(Modifier.padding(it)) {
                items(providers) {
                    ProviderItem(it, Modifier.clickable { providerDetails.value = it })
                }
            }
            providerDetails.value?.let { provider ->
                AlertDialog(
                    onDismissRequest = { providerDetails.value = null },
                    confirmButton = {},
                    title = { Text("Provider") },
                    text = {
                        ProviderScreen(provider = provider)
                    }
                )
            }
        }
    )
}

@Composable
private fun ProviderItem(provider: ProviderInfo, modifier: Modifier = Modifier) {
    ListItem(
        headlineText = {
            Text(provider.name.substringAfterLast("."))
        },
        modifier = modifier,
        supportingText = { Text(provider.name.substringBeforeLast(".")) }
    )
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