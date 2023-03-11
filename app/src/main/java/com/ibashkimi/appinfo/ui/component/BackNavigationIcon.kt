package com.ibashkimi.appinfo.ui.component

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun BackNavigationIcon(modifier: Modifier = Modifier) {
    val onBackDispatcher = LocalOnBackPressedDispatcherOwner.current
    IconButton(
        onClick = { onBackDispatcher?.onBackPressedDispatcher?.onBackPressed() },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
        )
    }
}