package com.ibashkimi.appinfo.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.ibashkimi.appinfo.Screen
import com.ibashkimi.appinfo.data.AppPackage
import com.ibashkimi.appinfo.data.DataManager
import com.ibashkimi.appinfo.navigateTo

@Composable
fun PackageCardSimple(app: AppPackage) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .clickable(onClick = {
                val packageInfo = DataManager.getPackage(context, app.packageInfo.packageName)
                navigateTo(Screen.AppDetails(packageInfo))
            })
            .padding(16.dp, 8.dp, 16.dp, 8.dp)
    ) {
        AppImage(modifier = Modifier.padding(end = 16.dp), app = app)
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(app.label, style = MaterialTheme.typography.subtitle1)
            Text(
                app.packageInfo.packageName,
                style = MaterialTheme.typography.subtitle2
            )
        }
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
