package com.ibashkimi.appinfo.ui.details.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.ibashkimi.appinfo.ui.details.*
import java.net.URLDecoder
import java.net.URLEncoder

private const val detailsRoute = "details_summary_route"
private const val activitiesRoute = "details_activities_route"
private const val permissionsRoute = "details_permissions_route"
private const val providersRoute = "details_providers_route"
private const val featuresRoute = "details_features_route"
private const val receiversRoute = "details_receivers_route"
private const val servicesRoute = "details_services_route"

private const val packageNameArg = "package_name"

internal data class PackageNameArg(
    val packageName: String,
) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        packageName = URLDecoder.decode(
            checkNotNull(savedStateHandle[packageNameArg])
        ),
    )
}

private fun packageNameArgument(): NamedNavArgument =
    navArgument(packageNameArg) { type = NavType.StringType }

internal fun NavController.navigateToDetails(
    packageName: String,
    navOptions: NavOptions? = null
) {
    val encodedPackageName = URLEncoder.encode(packageName)
    this.navigate("$detailsRoute/${encodedPackageName}", navOptions)
}

internal fun NavController.navigateToActivities(
    packageName: String,
    navOptions: NavOptions? = null
) {
    val encodedPackageName = URLEncoder.encode(packageName)
    this.navigate("$activitiesRoute/${encodedPackageName}", navOptions)
}

internal fun NavController.navigateToServices(
    packageName: String,
    navOptions: NavOptions? = null
) {
    val encodedPackageName = URLEncoder.encode(packageName)
    this.navigate("$servicesRoute/${encodedPackageName}", navOptions)
}

internal fun NavController.navigateToProviders(
    packageName: String,
    navOptions: NavOptions? = null
) {
    val encodedPackageName = URLEncoder.encode(packageName)
    this.navigate("$providersRoute/${encodedPackageName}", navOptions)
}

internal fun NavController.navigateToFeatures(
    packageName: String,
    navOptions: NavOptions? = null
) {
    val encodedPackageName = URLEncoder.encode(packageName)
    this.navigate("$featuresRoute/${encodedPackageName}", navOptions)
}

internal fun NavController.navigateToPermissions(
    packageName: String,
    navOptions: NavOptions? = null
) {
    val encodedPackageName = URLEncoder.encode(packageName)
    this.navigate("$permissionsRoute/${encodedPackageName}", navOptions)
}

internal fun NavController.navigateToReceivers(
    packageName: String,
    navOptions: NavOptions? = null
) {
    val encodedPackageName = URLEncoder.encode(packageName)
    this.navigate("$receiversRoute/${encodedPackageName}", navOptions)
}

fun NavGraphBuilder.detailsGraph(navController: NavController) {
    composable("$detailsRoute/{${packageNameArg}}", arguments = listOf(packageNameArgument())) {
        DetailsScreen(
            navigateToActivities = navController::navigateToActivities,
            navigateToServices = navController::navigateToServices,
            navigateToPermissions = navController::navigateToPermissions,
            navigateToFeatures = navController::navigateToFeatures,
            navigateToProviders = navController::navigateToProviders,
            navigateToReceivers = navController::navigateToReceivers,
        )
    }
    composable("$activitiesRoute/{${packageNameArg}}", arguments = listOf(packageNameArgument())) {
        ActivitiesScreen()
    }
    composable("$permissionsRoute/{${packageNameArg}}", arguments = listOf(packageNameArgument())) {
        PermissionsScreen()
    }
    composable("$providersRoute/{${packageNameArg}}", arguments = listOf(packageNameArgument())) {
        ProvidersScreen()
    }
    composable("$featuresRoute/{${packageNameArg}}", arguments = listOf(packageNameArgument())) {
        FeaturesScreen()
    }
    composable("$receiversRoute/{${packageNameArg}}", arguments = listOf(packageNameArgument())) {
        ReceiversScreen()
    }
    composable("$servicesRoute/{${packageNameArg}}", arguments = listOf(packageNameArgument())) {
        ServicesScreen()
    }
}