package com.ibashkimi.appinfo.ui.home

import androidx.compose.Composable
import androidx.core.graphics.drawable.toBitmap
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.Text
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.DrawImage
import androidx.ui.graphics.Image
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.Ripple
import androidx.ui.unit.dp
import com.ibashkimi.appinfo.Screen
import com.ibashkimi.appinfo.data.AppPackage
import com.ibashkimi.appinfo.data.DataManager
import com.ibashkimi.appinfo.navigateTo
import com.ibashkimi.appinfo.util.AndroidImage

@Composable
fun PackageCardSimple(app: AppPackage) {
    val context = ContextAmbient.current
    Ripple(bounded = true) {
        Clickable(onClick = {
            val packageInfo = DataManager.getPackage(context, app.packageInfo.packageName)
            navigateTo(Screen.AppDetails(packageInfo))
        }) {
            Row(modifier = LayoutPadding(16.dp, 8.dp, 16.dp, 8.dp)) {
                AppImage(modifier = LayoutPadding(right = 16.dp), app = app)
                Column(modifier = LayoutWidth.Fill) {
                    Text(app.label, style = ((MaterialTheme.typography()).subtitle1))
                    Text(
                        app.packageInfo.packageName,
                        style = ((MaterialTheme.typography()).subtitle2)
                    )
                }
            }
        }
    }
}

@Composable
fun AppImage(modifier: Modifier = Modifier.None, app: AppPackage) {
    val context = ContextAmbient.current
    Container(modifier = modifier + LayoutSize(40.dp, 40.dp)) {
        val image: Image =
            AndroidImage(app.packageInfo.applicationInfo.loadIcon(context.packageManager).toBitmap())
        DrawImage(image)
    }
}
