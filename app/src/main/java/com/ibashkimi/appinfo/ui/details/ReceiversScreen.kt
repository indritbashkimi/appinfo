package com.ibashkimi.appinfo.ui.details

import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ibashkimi.appinfo.R
import com.ibashkimi.appinfo.ui.component.BackNavigationIcon
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

@Composable
fun ReceiversScreen(modifier: Modifier = Modifier, viewModel: DetailsViewModel = hiltViewModel()) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = { Text(stringResource(id = R.string.receivers)) },
                navigationIcon = {
                    BackNavigationIcon()
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = {
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            val receivers: ImmutableList<ActivityInfo> = remember(uiState.value) {
                uiState.value?.receivers?.asList()?.toImmutableList() ?: persistentListOf()
            }
            LazyColumn(Modifier.padding(it)) {
                items(receivers) {
                    ReceiverItem(it)
                }
            }
        }
    )
}

@Composable
private fun ReceiverItem(activityInfo: ActivityInfo, modifier: Modifier = Modifier) {
    ListItem(
        headlineContent = {
            Text(activityInfo.name.substringAfterLast("."))
        },
        modifier = modifier,
        supportingContent = { Text(activityInfo.name.substringBeforeLast(".")) }
    )
}