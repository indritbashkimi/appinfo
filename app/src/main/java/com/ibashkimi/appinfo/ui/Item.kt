package com.ibashkimi.appinfo.ui

import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.foundation.Clickable
import androidx.ui.layout.Column
import androidx.ui.layout.LayoutWidth
import androidx.ui.layout.Padding
import androidx.ui.layout.Row
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.Ripple
import androidx.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp

@Composable
fun Title(text: String) {
    Text(text, style = ((MaterialTheme.typography()).subtitle2))
}

@Composable
fun Summary(text: String) {
    Text(text, style = ((MaterialTheme.typography()).subtitle1))
}

@Composable
fun Item(title: String, summary: String) {
    Padding(16.dp, 8.dp, 16.dp, 8.dp) {
        Row {
            Column(modifier = LayoutWidth.Fill) {
                Title(title)
                Summary(summary)
            }
        }
    }
}

@Composable
fun Item(title: Int, summary: String) {
    Item(stringResource(title), summary)
}

@Composable
fun ClickableItem(title: String, summary: String, onClick: () -> Unit = {}) {
    Ripple(bounded = true) {
        Clickable(onClick = onClick) {
            Item(title, summary)
        }
    }
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