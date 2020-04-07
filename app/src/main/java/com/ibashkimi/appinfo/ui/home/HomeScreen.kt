package com.ibashkimi.appinfo.ui.home

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.drawOpacity
import androidx.ui.foundation.AdapterList
import androidx.ui.foundation.Text
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.wrapContentSize
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
    Text(
        text = stringResource(R.string.loading),
        style = MaterialTheme.typography.h5,
        modifier = Modifier.fillMaxSize() + Modifier.wrapContentSize(Alignment.Center) + Modifier.drawOpacity(0.5f)
    )
}

@Composable
private fun PackagesLoaded(packages: List<AppPackage>) {
    android.util.Log.d("HomeScreen", "packagesLoaded: ${packages.size}")
    AdapterList(data = packages) {
        PackageCardSimple(it)
    }
}
