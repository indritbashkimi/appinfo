package com.ibashkimi.appinfo.ui.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
        modifier = modifier.fillMaxWidth().padding(
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