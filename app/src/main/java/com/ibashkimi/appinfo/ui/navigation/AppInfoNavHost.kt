package com.ibashkimi.appinfo.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ibashkimi.appinfo.ui.details.navigation.detailsGraph
import com.ibashkimi.appinfo.ui.details.navigation.navigateToDetails
import com.ibashkimi.appinfo.ui.home.HomeRoute

@Composable
fun AppInfoNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home_route",
        modifier = modifier,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
    ) {
        composable("home_route") {
            HomeRoute(
                navigateToAppDetails = navController::navigateToDetails
            )
        }
        detailsGraph(navController)
    }
}
