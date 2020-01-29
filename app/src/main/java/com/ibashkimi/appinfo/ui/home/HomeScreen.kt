package com.ibashkimi.appinfo.ui.home

import androidx.compose.Composable
import androidx.compose.ambient
import androidx.compose.unaryPlus
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.Center
import androidx.ui.layout.Column
import androidx.ui.material.MaterialTheme
import androidx.ui.material.withOpacity
import com.ibashkimi.appinfo.R
import com.ibashkimi.appinfo.data.AppPackage
import com.ibashkimi.appinfo.data.Request
import com.ibashkimi.appinfo.data.Result


@Composable
fun HomeScreen(packages: Request<List<AppPackage>>) {
    when (val apps = packages.result) {
        is Result.Loading -> {
            LoadingPackages()
        }
        is Result.Success -> {
            PackagesLoaded(packages = apps.result)
        }
    }
}

@Composable
private fun LoadingPackages() {
    val context = +ambient(ContextAmbient)
    Center {
        Text(
            text = context.getString(R.string.loading),
            style = ((+MaterialTheme.typography()).h5).withOpacity(0.5f)
        )
    }
}

@Composable
private fun PackagesLoaded(packages: List<AppPackage>) {
    android.util.Log.d("HomeScreen", "packagesLoaded: ${packages.size}")
    VerticalScroller {
        Column {
            packages.forEach {
                PackageCardSimple(it)
            }
        }
    }
}
