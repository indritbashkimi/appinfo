package com.ibashkimi.appinfo.ui.home

import androidx.compose.foundation.Image
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.ibashkimi.appinfo.Screen
import com.ibashkimi.appinfo.data.AppPackage
import com.ibashkimi.appinfo.data.DataManager
import com.ibashkimi.appinfo.navigateTo

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PackageCardSimple(app: AppPackage, modifier: Modifier = Modifier) {
    ListItem(modifier = modifier,
        icon = {
            AppImage(app = app)
        },
        secondaryText = {
            Text(
                app.packageInfo.packageName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }) {
        Text(
            app.label,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun AppImage(modifier: Modifier = Modifier, app: AppPackage) {
    val context = LocalContext.current
    Box(modifier = modifier.preferredSize(40.dp, 40.dp)) {
        Image(
            app.packageInfo.applicationInfo.loadIcon(context.packageManager).toBitmap()
                .asImageBitmap(),
            contentDescription = null
        )
    }
}
