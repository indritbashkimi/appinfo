package com.ibashkimi.appinfo.ui.details

import android.content.pm.ServiceInfo
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
fun ServicesScreen(modifier: Modifier = Modifier, viewModel: DetailsViewModel = hiltViewModel()) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = { Text(stringResource(id = R.string.services)) },
                navigationIcon = {
                    BackNavigationIcon()
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = {
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            val services: ImmutableList<ServiceInfo> = remember(uiState.value) {
                uiState.value?.services?.asList()?.toImmutableList() ?: persistentListOf()
            }
            val serviceDetails = remember { mutableStateOf<ServiceInfo?>(null) }
            LazyColumn(Modifier.padding(it)) {
                items(services) {
                    ServiceItem(
                        service = it,
                        modifier = Modifier.clickable { serviceDetails.value = it })
                }
            }
            serviceDetails.value?.let { service ->
                AlertDialog(
                    onDismissRequest = { serviceDetails.value = null },
                    confirmButton = {},
                    title = { Text("Service") },
                    text = {
                        ServiceScreen(service = service)
                    }
                )
            }
        }
    )
}

@Composable
private fun ServiceItem(service: ServiceInfo, modifier: Modifier) {
    ListItem(
        headlineText = {
            Text(service.name.substringAfterLast("."))
        },
        modifier = modifier,
        supportingText = {
            Text(service.name.substringBeforeLast("."))
        }
    )
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