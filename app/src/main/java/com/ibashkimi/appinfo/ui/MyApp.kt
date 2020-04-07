package com.ibashkimi.appinfo.ui

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.isSystemInDarkTheme
import androidx.ui.graphics.ColorFilter
import androidx.ui.layout.padding
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Scaffold
import androidx.ui.material.Surface
import androidx.ui.material.TopAppBar
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.ArrowBack
import androidx.ui.material.ripple.ripple
import androidx.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.ibashkimi.appinfo.*
import com.ibashkimi.appinfo.data.DataManager
import com.ibashkimi.appinfo.ui.details.*
import com.ibashkimi.appinfo.ui.home.HomeScreen


@Composable
fun MyApp() {
    val isDark = isSystemInDarkTheme()
    MaterialTheme(
        colors = if (isDark) darkThemeColors else lightThemeColors
    ) {
        val destination = Status.currentScreen
        Scaffold(
            topAppBar = {
                val title: String = stringResource(destination.titleStringRes)
                if (destination == Screen.Home) {
                    TopAppBar(title = { Text(title) })
                } else {
                    TopAppBar(
                        title = { Text(title) },
                        navigationIcon = {
                            Clickable(
                                onClick = { Navigation.pop() },
                                modifier = Modifier.ripple() + Modifier.padding(16.dp)
                            ) {
                                Image(
                                    asset = Icons.Default.ArrowBack,
                                    alignment = Alignment.Center,
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
                val context = ContextAmbient.current
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
