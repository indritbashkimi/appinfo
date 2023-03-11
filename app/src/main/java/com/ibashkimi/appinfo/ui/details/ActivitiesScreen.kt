package com.ibashkimi.appinfo.ui.details

import android.content.pm.ActivityInfo
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
internal fun ActivitiesScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = { Text(stringResource(id = R.string.activities)) },
                navigationIcon = {
                    BackNavigationIcon()
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = { paddingValues ->
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            val activities: ImmutableList<ActivityInfo> = remember(uiState.value) {
                uiState.value?.activities?.asList()?.toImmutableList() ?: persistentListOf()
            }
            val activityDetails = remember { mutableStateOf<ActivityInfo?>(null) }
            LazyColumn(Modifier.padding(paddingValues)) {
                items(activities) {
                    ActivityItem(it, Modifier.clickable { activityDetails.value = it })
                }
            }
            activityDetails.value?.let { activity ->
                AlertDialog(
                    onDismissRequest = { activityDetails.value = null },
                    confirmButton = {},
                    title = { Text(stringResource(id = R.string.activity_info)) },
                    text = {
                        ActivityScreen(activity = activity)
                    }
                )
            }
        }
    )
}

@Composable
private fun ActivityItem(activity: ActivityInfo, modifier: Modifier = Modifier) {
    ListItem(
        headlineText = {
            Text(activity.name.substringAfterLast("."))
        },
        modifier = modifier,
        supportingText = {
            Text(activity.name.substringBeforeLast("."))
        }
    )
}

@Composable
fun ActivityScreen(activity: ActivityInfo) {
    Column {
        val className = activity.name.substringAfterLast(".")
        val classPackage = activity.name.substringBeforeLast(".")
        Item(R.string.component_name, className)
        Item(R.string.component_package, classPackage)
        Item(R.string.target_activity, activity.targetActivity ?: stringResource(R.string.none))
        Item(R.string.task_affinity, activity.taskAffinity ?: stringResource(R.string.none))
        Item(R.string.component_permission, activity.permission ?: stringResource(R.string.none))
        Item(R.string.component_process_name, activity.processName)
        Item(R.string.component_enabled, activity.enabled.toString())
        Item(R.string.component_exported, activity.exported.toString())
    }
}
