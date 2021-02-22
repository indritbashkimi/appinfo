package com.ibashkimi.appinfo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ibashkimi.appinfo.*
import com.ibashkimi.appinfo.R
import com.ibashkimi.appinfo.data.DataManager
import com.ibashkimi.appinfo.ui.details.*
import com.ibashkimi.appinfo.ui.home.HomeScreen


@Composable
fun MyApp() {
    val isDark = isSystemInDarkTheme()
    MaterialTheme(
        colors = if (isDark) darkThemeColors else lightThemeColors
    ) {
        val destination = Status.currentScreen.value
        Scaffold(
            topBar = {
                val title: String = stringResource(destination.titleStringRes)
                if (destination == Screen.Home) {
                    TopAppBar(title = { Text(title) })
                } else {
                    TopAppBar(
                        title = { Text(title) },
                        navigationIcon = {
                            IconButton(onClick = { Navigation.pop() }) {
                                Image(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
                                )
                            }
                        })
                }
            },
            bodyContent = {
                AppContent(destination)
            }
        )
    }
}

@Composable
private fun AppContent(screen: Screen) {
    android.util.Log.d("MyApp", "new screen: $screen")
    Surface(color = MaterialTheme.colors.background) {
        when (screen) {
            is Screen.Home -> {
                val context = LocalContext.current
                HomeScreen(DataManager.getPackages(context))
            }
            is Screen.AppDetails -> DetailsScreen(request = screen.request)
            is Screen.Activities -> ActivitiesScreen(activities = screen.activities)
            is Screen.Activity -> ActivityScreen(activity = screen.activity)
            is Screen.Services -> ServicesScreen(services = screen.services)
            is Screen.Service -> ServiceScreen(service = screen.service)
            is Screen.Permissions -> PermissionsScreen(packageInfo = screen.packageInfo)
            is Screen.Providers -> ProvidersScreen(providers = screen.providers)
            is Screen.Provider -> ProviderScreen(provider = screen.provider)
            is Screen.Receivers -> ReceiversScreen(receivers = screen.receivers)
            is Screen.Features -> FeaturesScreen(features = screen.features)
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApp()
}


private val Screen.titleStringRes: Int
    get() = when (this) {
        is Screen.Home -> R.string.app_name
        is Screen.AppDetails -> R.string.details
        is Screen.Activities -> R.string.activities
        is Screen.Activity -> R.string.activity_info
        is Screen.Services -> R.string.services
        is Screen.Service -> R.string.service_info
        is Screen.Permissions -> R.string.permissions
        is Screen.Providers -> R.string.providers
        is Screen.Provider -> R.string.provider
        is Screen.Receivers -> R.string.receivers
        is Screen.Features -> R.string.features
    }
