package com.ibashkimi.appinfo.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
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
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
            .alpha(0.5f)
    )
}

@Composable
private fun PackagesLoaded(packages: List<AppPackage>) {
    android.util.Log.d("HomeScreen", "packagesLoaded: ${packages.size}")
    LazyColumn {
        items(packages) {
            PackageCardSimple(it)
        }
    }
}
