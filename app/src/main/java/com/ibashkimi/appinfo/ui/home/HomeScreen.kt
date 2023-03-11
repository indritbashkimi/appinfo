package com.ibashkimi.appinfo.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ibashkimi.appinfo.R
import com.ibashkimi.appinfo.data.AppPackage
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun HomeRoute(
    navigateToAppDetails: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(uiState = uiState, navigateToAppDetails = navigateToAppDetails, modifier = modifier)
}

@Composable
internal fun HomeScreen(
    uiState: State<HomeUiState>,
    navigateToAppDetails: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                scrollBehavior = scrollBehavior
            )
        },
        content = {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                PackagesLoaded(
                    packages = { uiState.value.packages },
                    navigateToAppDetails = navigateToAppDetails
                )
                val isLoading = remember {
                    derivedStateOf { uiState.value.isLoading }
                }
                if (isLoading.value) {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            }
        }
    )
}

@Composable
private fun PackagesLoaded(
    packages: () -> ImmutableList<AppPackage>,
    navigateToAppDetails: (String) -> Unit
) {
    LazyColumn {
        items(packages(), key = { it.packageInfo.packageName }) {
            AppListItem(
                packageName = it.packageInfo.packageName,
                label = it.label,
                modifier = Modifier.clickable(onClick = {
                    navigateToAppDetails(it.packageInfo.packageName)
                })
            )
        }
    }
}
