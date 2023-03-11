package com.ibashkimi.appinfo.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun Title(text: String) {
    Text(text, style = MaterialTheme.typography.titleSmall)
}

@Composable
fun Summary(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun Item(title: String, summary: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp, 16.dp, 8.dp)
    ) {
        Title(title)
        Summary(summary)
    }
}

@Composable
fun Item(title: Int, summary: String, modifier: Modifier = Modifier) {
    Item(stringResource(title), summary, modifier)
}
