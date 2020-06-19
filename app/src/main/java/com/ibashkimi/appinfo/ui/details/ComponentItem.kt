package com.ibashkimi.appinfo.ui.details

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.material.MaterialTheme
import androidx.ui.unit.dp

@Composable
fun ComponentTitle(text: String) {
    Text(text, style = MaterialTheme.typography.subtitle1)
}

@Composable
fun ComponentSummary(text: String) {
    Text(text, style = MaterialTheme.typography.subtitle2)
}

@Composable
fun ComponentItem(title: String, summary: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier + Modifier.fillMaxWidth() + Modifier.padding(
            16.dp,
            8.dp,
            16.dp,
            8.dp
        )
    ) {
        ComponentTitle(title)
        ComponentSummary(summary)
    }
}

@Composable
fun ClickableComponentItem(title: String, summary: String, onClick: () -> Unit = {}) {
    ComponentItem(
        title,
        summary,
        modifier = Modifier.clickable(onClick = onClick)
    )
}