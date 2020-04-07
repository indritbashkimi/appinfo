package com.ibashkimi.appinfo.ui.details

import android.content.pm.FeatureInfo
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.res.stringResource
import androidx.ui.unit.dp
import com.ibashkimi.appinfo.R

@Composable
fun FeaturesScreen(features: Array<FeatureInfo>) {
    VerticalScroller {
        Column {
            features.forEach { feature ->
                val (name: String, version: String) = feature.name?.let {
                    Pair(
                        it,
                        feature.version.toString()
                    )
                }
                    ?: feature.glEsVersion?.let { Pair("android:glEsVersion", it) }
                    ?: Pair(stringResource(R.string.unknown), feature.version.toString())

                FeatureItem(name, version, feature.flags == 1)
            }
        }
    }
}

@Composable
private fun FeatureItem(name: String, version: String, required: Boolean) {
    Column(modifier = Modifier.fillMaxWidth() + Modifier.padding(16.dp, 8.dp, 16.dp, 8.dp)) {
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