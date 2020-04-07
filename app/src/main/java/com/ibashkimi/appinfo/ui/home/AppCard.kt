package com.ibashkimi.appinfo.ui.home

import androidx.compose.Composable
import androidx.core.graphics.drawable.toBitmap
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.graphics.asImageAsset
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.ripple
import androidx.ui.unit.dp
import com.ibashkimi.appinfo.Screen
import com.ibashkimi.appinfo.data.AppPackage
import com.ibashkimi.appinfo.data.DataManager
import com.ibashkimi.appinfo.navigateTo

@Composable
fun PackageCardSimple(app: AppPackage) {
    val context = ContextAmbient.current
    Clickable(onClick = {
        val packageInfo = DataManager.getPackage(context, app.packageInfo.packageName)
        navigateTo(Screen.AppDetails(packageInfo))
    }, modifier = Modifier.ripple()) {
        Row(modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 8.dp)) {
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
}

@Composable
fun AppImage(modifier: Modifier = Modifier.None, app: AppPackage) {
    val context = ContextAmbient.current
    Box(modifier = modifier + Modifier.preferredSize(40.dp, 40.dp)) {
        Image(
            app.packageInfo.applicationInfo.loadIcon(context.packageManager).toBitmap()
                .asImageAsset()
        )
    }
}
