package com.ibashkimi.appinfo.ui.details

import android.content.pm.FeatureInfo
import androidx.compose.foundation.layout.Column
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
fun FeaturesScreen(modifier: Modifier = Modifier, viewModel: DetailsViewModel = hiltViewModel()) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = { Text(stringResource(id = R.string.features)) },
                navigationIcon = {
                    BackNavigationIcon()
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = { paddingValues ->
            val uiState = viewModel.uiState.collectAsStateWithLifecycle()
            val features: ImmutableList<FeatureInfo> = remember(uiState.value) {
                uiState.value?.reqFeatures?.asList()?.toImmutableList() ?: persistentListOf()
            }
            LazyColumn(Modifier.padding(paddingValues)) {
                items(features) { feature ->
                    val (name: String, version: String) = feature.name?.let {
                        Pair(
                            it,
                            feature.version.toString()
                        )
                    } ?: feature.glEsVersion?.let { Pair("android:glEsVersion", it) }
                    ?: Pair(stringResource(R.string.unknown), feature.version.toString())

                    FeatureItem(name, version, feature.flags == 1)
                }
            }
        }
    )
}

@Composable
private fun FeatureItem(name: String, version: String, required: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp, 16.dp, 8.dp)
    ) {
        Text(name, style = MaterialTheme.typography.titleMedium)
        Text(
            stringResource(R.string.feature_version, version),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            stringResource(if (required) R.string.feature_required else R.string.feature_not_required),
            style = MaterialTheme.typography.titleSmall
        )
    }
}