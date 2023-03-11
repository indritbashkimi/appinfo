package com.ibashkimi.appinfo.ui.home

import androidx.compose.runtime.Stable
import com.ibashkimi.appinfo.data.AppPackage
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
data class HomeUiState(
    val isLoading: Boolean = true,
    val packages: ImmutableList<AppPackage> = persistentListOf(),
)