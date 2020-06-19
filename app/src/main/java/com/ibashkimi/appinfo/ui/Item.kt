package com.ibashkimi.appinfo.ui

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.material.MaterialTheme
import androidx.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp

@Composable
fun Title(text: String) {
    Text(text, style = MaterialTheme.typography.subtitle2)
}

@Composable
fun Summary(text: String) {
    Text(text, style = MaterialTheme.typography.subtitle1)
}

@Composable
fun Item(title: String, summary: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier + Modifier.fillMaxWidth() + Modifier.padding(16.dp, 8.dp, 16.dp, 8.dp)) {
        Title(title)
        Summary(summary)
    }
}

@Composable
fun Item(title: Int, summary: String, modifier: Modifier = Modifier) {
    Item(stringResource(title), summary, modifier)
}

@Composable
fun ClickableItem(title: String, summary: String, onClick: () -> Unit = {}) {
    Item(title, summary, modifier = Modifier.clickable(onClick = onClick))
}

@Composable
fun ClickableItem(title: Int, summary: String, onClick: () -> Unit = {}) {
    ClickableItem(stringResource(title), summary, onClick)
}

@Preview
@Composable
fun clickableItemPreview() {
    ClickableItem(title = "App Info", summary = "Examine app info")
}