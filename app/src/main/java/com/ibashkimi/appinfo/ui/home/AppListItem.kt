package com.ibashkimi.appinfo.ui.home

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
internal fun AppListItem(packageName: String, label: String, modifier: Modifier = Modifier) {
    ListItem(
        modifier = modifier,
        headlineText = {
            Text(
                label,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        leadingContent = {
            AppImage(packageName)
        },
        supportingText = {
            Text(
                packageName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        })
}

@Composable
private fun AppImage(packageName: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    /*val drawable = remember(context, packageName) {
        try {
            context.packageManager.getApplicationIcon(packageName)
        } catch (e: Exception) {
            null
        }
    }*/
    val drawable = remember { mutableStateOf<Drawable?>(null) }
    LaunchedEffect(context, packageName) {
        // This approach feels faster
        withContext(Dispatchers.IO) {
            drawable.value = try {
                context.packageManager.getApplicationIcon(packageName)
            } catch (e: Exception) {
                null
            }
        }
    }
    Box(modifier = modifier.size(40.dp, 40.dp), contentAlignment = Alignment.Center) {
        Image(rememberDrawablePainter(drawable = drawable.value), contentDescription = null)
    }
}

