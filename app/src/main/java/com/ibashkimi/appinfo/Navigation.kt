package com.ibashkimi.appinfo

import android.content.pm.*
import androidx.compose.Model
import com.ibashkimi.appinfo.data.AppPackage
import com.ibashkimi.appinfo.data.Request

/**
 * Class defining the screens we have in the app: home, article details and interests
 */
sealed class Screen {
    object Home : Screen()
    data class AppDetails(val request: Request<PackageInfo>) : Screen()
    class Activities(val activities: Array<ActivityInfo>) : Screen()
    data class Activity(val activity: ActivityInfo) : Screen()
    class Services(val services: Array<ServiceInfo>) : Screen()
    data class Service(val service: ServiceInfo) : Screen()
    class Permissions(val packageInfo: PackageInfo) : Screen()
    class Providers(val providers: Array<ProviderInfo>) : Screen()
    class Provider(val provider: ProviderInfo): Screen()
    class Receivers(val receivers: Array<ActivityInfo>) : Screen()
    class Features(val features: Array<FeatureInfo>) : Screen()
}

@Model
object Status {
    var currentScreen: Screen = Screen.Home
}

object Navigation {

    private val stack = ArrayList<Screen>().apply { add(Screen.Home) }

    fun push(destination: Screen) {
        stack.add(destination)
        stackChanged()
    }

    fun pop(): Boolean {
        if (stack.size < 2)
            return false
        stack.removeAt(stack.size - 1)
        stackChanged()
        return true
    }

    fun replace(destination: Screen) {
        stack[stack.size - 1] = destination
        stackChanged()
    }

    private fun stackChanged() {
        Status.currentScreen = stack.last()
    }
}

/**
 * Temporary solution pending navigation support.
 */
fun navigateTo(destination: Screen) {
    Navigation.push(destination)
}