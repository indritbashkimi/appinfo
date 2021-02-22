package com.ibashkimi.appinfo.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
    Column(modifier = modifier.fillMaxWidth().padding(16.dp, 8.dp, 16.dp, 8.dp)) {
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
fun ClickableItemPreview() {
    ClickableItem(title = "App Info", summary = "Examine app info")
}