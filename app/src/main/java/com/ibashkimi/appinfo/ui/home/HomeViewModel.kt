package com.ibashkimi.appinfo.ui.home

import android.app.Application
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibashkimi.appinfo.data.AppPackage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val application: Application
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> =
        flow { emit(fetchPackages()) }.map { packages ->
            HomeUiState(
                isLoading = false,
                packages = packages.sortedBy { it.label }.toImmutableList()
            )
        }.flowOn(Dispatchers.IO).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            HomeUiState()
        )

    fun getPackage(packageName: String): Request<PackageInfo> {
        val request = Request<PackageInfo>()
        CoroutineScope(Dispatchers.IO).launch {
            val packageInfo = fetchPackage(application, packageName)
            withContext(Dispatchers.Main) {
                request.result = Result.Success(packageInfo)
            }
        }

        return request
    }

    fun getPackageFlow(packageName: String): Flow<PackageInfo> {
        return flowOf(fetchPackage(application, packageName)).flowOn(Dispatchers.IO)
    }

    private fun fetchPackages(): List<AppPackage> {
        val packageManager = application.packageManager
        return packageManager.getInstalledPackages(PackageManager.GET_META_DATA).map {
            AppPackage(
                it,
                it.applicationInfo.loadLabel(packageManager).toString()
            )
        }
    }

    private fun fetchPackage(context: Context, packageName: String): PackageInfo =
        context.packageManager.getPackageInfo(
            packageName,
            PackageManager.GET_ACTIVITIES or
                    PackageManager.GET_SERVICES or
                    PackageManager.GET_RECEIVERS or
                    PackageManager.GET_PROVIDERS or
                    PackageManager.GET_PERMISSIONS or
                    PackageManager.GET_CONFIGURATIONS // Features
        )
}

class Request<T>(var result: Result<T> = Result.Loading())

sealed class Result<T> {
    class Loading<T> : Result<T>()
    class Success<T>(val result: T) : Result<T>()
}