package com.ibashkimi.appinfo.ui.details

import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.Clickable
import androidx.ui.layout.Column
import androidx.ui.layout.Padding
import androidx.ui.layout.Row
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.Ripple

@Composable
fun ComponentTitle(text: String) {
    Text(text, style = ((+MaterialTheme.typography()).subtitle1))
}

@Composable
fun ComponentSummary(text: String) {
    Text(text, style = ((+MaterialTheme.typography()).subtitle2))
}

@Composable
fun ComponentItem(title: String, summary: String) {
    Padding(16.dp, 8.dp, 16.dp, 8.dp) {
        Row {
            Column(modifier = Flexible(1f)) {
                ComponentTitle(title)
                ComponentSummary(summary)
            }
        }
    }
}

@Composable
fun ClickableComponentItem(title: String, summary: String, onClick: () -> Unit = {}) {
    Ripple(bounded = true) {
        Clickable(onClick = onClick) {
            ComponentItem(title, summary)
        }
    }
}