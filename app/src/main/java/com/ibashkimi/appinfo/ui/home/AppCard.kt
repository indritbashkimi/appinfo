package com.ibashkimi.appinfo.ui.home

import android.content.pm.PackageInfo
import androidx.compose.Composable
import androidx.compose.Context
import androidx.compose.ambient
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
import com.ibashkimi.appinfo.data.Request
import com.ibashkimi.appinfo.data.Result
import com.ibashkimi.appinfo.navigateTo
import com.ibashkimi.appinfo.util.AndroidImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun PackageCardSimple(app: AppPackage) {
    val context = ambient(ContextAmbient)
    Ripple(bounded = true) {
        Clickable(onClick = {
            val packageInfo = DataManager.getPackage(context, app.packageInfo.packageName)
            navigateTo(Screen.AppDetails(packageInfo))
        }) {
            Padding(16.dp, 8.dp, 16.dp, 8.dp) {
                Row {
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
}

@Composable
fun AppImage(modifier: Modifier = Modifier.None, app: AppPackage) {
    val context = ambient(ContextAmbient)
    val request = loadImage(context, app.packageInfo)
    Container(modifier = modifier + LayoutSize(40.dp, 40.dp)) {
        when (val image = request.result) {
            is Result.Loading -> {
            }
            is Result.Success -> {
                DrawImage(image.result)
            }
        }
    }
}

private fun loadImage(context: Context, packageInfo: PackageInfo): Request<Image> {
    val request = Request<Image>()
    CoroutineScope(Dispatchers.IO).launch {
        val image: Image =
            AndroidImage(packageInfo.applicationInfo.loadIcon(context.packageManager).toBitmap())
        withContext(Dispatchers.Main) {
            request.result = Result.Success(image)
        }
    }
    return request
}