package com.ibashkimi.appinfo.data

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.compose.Model
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DataManager {

    private var packages: List<AppPackage>? = null

    fun getPackages(context: Context): Request<List<AppPackage>> {
        val request = Request<List<AppPackage>>()
        packages?.also {
            request.result = Result.Success(it)
        } ?: CoroutineScope(Dispatchers.IO).launch {
            val newData = fetchPackages(context).sortedBy { it.label }//.take(50)
            packages = newData
            withContext(Dispatchers.Main) {
                request.result = Result.Success(newData)
            }
        }

        return request
    }

    fun getPackage(context: Context, packageName: String): Request<PackageInfo> {
        val request = Request<PackageInfo>()
        CoroutineScope(Dispatchers.IO).launch {
            val packageInfo = fetchPackage(context, packageName)
            withContext(Dispatchers.Main) {
                request.result = Result.Success(packageInfo)
            }
        }

        return request
    }

    private fun packagesFlow(context: Context): Flow<Result<List<AppPackage>>> = flow {
        emit(Result.Loading())
        emit(Result.Success(fetchPackages(context)))
    }

    private fun fetchPackages(context: Context): List<AppPackage> {
        val packageManager = context.packageManager
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

@Model
class Request<T>(var result: Result<T> = Result.Loading())

sealed class Result<T> {
    class Loading<T> : Result<T>()
    class Success<T>(val result: T) : Result<T>()
}