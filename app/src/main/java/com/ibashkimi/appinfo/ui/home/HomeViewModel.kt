package com.ibashkimi.appinfo.ui.home

import android.app.Application
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibashkimi.appinfo.data.AppPackage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
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

    private fun fetchPackages(): List<AppPackage> {
        val packageManager = application.packageManager
        return packageManager.getInstalledPackages(PackageManager.GET_META_DATA).map {
            AppPackage(
                it,
                it.applicationInfo.loadLabel(packageManager).toString()
            )
        }
    }
}
