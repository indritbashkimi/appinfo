package com.ibashkimi.appinfo.ui.details

import android.content.pm.FeatureInfo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ibashkimi.appinfo.R

@Composable
fun FeaturesScreen(features: Array<FeatureInfo>) {
    LazyColumn {
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

@Composable
private fun FeatureItem(name: String, version: String, required: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp, 16.dp, 8.dp)
    ) {
        Text(name, style = MaterialTheme.typography.subtitle1)
        Text(
            stringResource(R.string.feature_version, version),
            style = MaterialTheme.typography.subtitle1
        )
        Text(
            stringResource(if (required) R.string.feature_required else R.string.feature_not_required),
            style = MaterialTheme.typography.subtitle2
        )
    }
}