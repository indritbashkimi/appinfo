package com.ibashkimi.appinfo.ui.home

import androidx.compose.Composable
import androidx.ui.core.Opacity
import androidx.ui.core.Text
import androidx.ui.foundation.AdapterList
import androidx.ui.layout.Center
import androidx.ui.material.MaterialTheme
import androidx.ui.res.stringResource
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
    Center {
        Opacity(opacity = 0.5f) {
            Text(
                text = stringResource(R.string.loading),
                style = ((MaterialTheme.typography()).h5)
            )
        }
    }
}

@Composable
private fun PackagesLoaded(packages: List<AppPackage>) {
    android.util.Log.d("HomeScreen", "packagesLoaded: ${packages.size}")
    AdapterList(data = packages) {
        PackageCardSimple(it)
    }
}
