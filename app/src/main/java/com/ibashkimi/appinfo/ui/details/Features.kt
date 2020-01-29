package com.ibashkimi.appinfo.ui.details

import android.content.pm.FeatureInfo
import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Column
import androidx.ui.layout.Padding
import androidx.ui.layout.Row
import androidx.ui.material.MaterialTheme
import androidx.ui.res.stringResource
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
                    ?: Pair(+stringResource(R.string.unknown), feature.version.toString())

                FeatureItem(name, version, feature.flags == 1)
            }
        }
    }
}

@Composable
private fun FeatureItem(name: String, version: String, required: Boolean) {
    Padding(16.dp, 8.dp, 16.dp, 8.dp) {
        Row {
            Column(modifier = Flexible(1f)) {
                Text(name, style = ((+MaterialTheme.typography()).subtitle1))
                Text(
                    +stringResource(R.string.feature_version, version)
                    , style = ((+MaterialTheme.typography()).subtitle1)
                )
                Text(
                    +stringResource(if (required) R.string.feature_required else R.string.feature_not_required)
                    ,
                    style = ((+MaterialTheme.typography()).subtitle2)
                )
            }
        }
    }
}