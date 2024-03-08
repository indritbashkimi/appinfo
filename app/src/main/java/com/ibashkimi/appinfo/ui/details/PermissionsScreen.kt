package com.ibashkimi.appinfo.ui.details

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ibashkimi.appinfo.R
import com.ibashkimi.appinfo.ui.component.BackNavigationIcon
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun PermissionsScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = { Text(stringResource(id = R.string.permissions)) },
                navigationIcon = {
                    BackNavigationIcon()
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = {
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            val permissions: ImmutableList<String> = remember(uiState.value) {
                uiState.value?.requestedPermissions?.sorted()?.toImmutableList()
                    ?: persistentListOf()
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)
            ) {
                items(permissions) {
                    PermissionItem(it)
                }
            }
        }
    )
}

@Composable
private fun PermissionItem(permission: String) {
    ListItem(
        headlineContent = { Text(permission) },
        modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 8.dp)
    )
}