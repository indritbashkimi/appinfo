package com.ibashkimi.appinfo.ui.details

import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.foundation.Clickable
import androidx.ui.layout.Column
import androidx.ui.layout.LayoutPadding
import androidx.ui.layout.LayoutWidth
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.Ripple
import androidx.ui.unit.dp

@Composable
fun ComponentTitle(text: String) {
    Text(text, style = ((MaterialTheme.typography()).subtitle1))
}

@Composable
fun ComponentSummary(text: String) {
    Text(text, style = ((MaterialTheme.typography()).subtitle2))
}

@Composable
fun ComponentItem(title: String, summary: String) {
    Column(modifier = LayoutWidth.Fill + LayoutPadding(16.dp, 8.dp, 16.dp, 8.dp)) {
        ComponentTitle(title)
        ComponentSummary(summary)
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