package com.outivox.myabc.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.outivox.core.navigation.DashboardScreenDestination
import com.outivox.core.navigation.LaunchableSampleScreenDestination
import com.outivox.core.navigation.SplashScreenDestination
import com.outivox.core.util.JsonExtensions.fromJson
import com.outivox.myabc.ui.presentation.dashboard.DashboardScreen
import com.outivox.myabc.ui.presentation.sample.SampleScreen
import com.outivox.myabc.ui.presentation.splash.SplashScreen

fun NavGraphBuilder.mainNavGraph() {
    // TODO 8: Define the navigation destination and create the composable screen for it
    composable<SplashScreenDestination> {
        SplashScreen()
    }

    composable<DashboardScreenDestination> {
        val backStackEntry = it.toRoute<DashboardScreenDestination>()
        val data = backStackEntry.dataJson.fromJson<Map<String, String>>()
        DashboardScreen(
            data = data?.entries?.find { it.key == "key3" }?.value.orEmpty()
        )
    }

    composable<LaunchableSampleScreenDestination> {
        SampleScreen()
    }
}