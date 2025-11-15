package com.outivox.myabc.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.outivox.authentication.ui.presentation.login.navigation.authNavGraph
import com.outivox.core.navigation.SplashScreenDestination
import com.outivox.core.navigation.slideInFromEnd
import com.outivox.core.navigation.slideInFromStart
import com.outivox.core.navigation.slideOutToEnd
import com.outivox.core.navigation.slideOutToStart
import com.outivox.core.util.LocalNavController
import com.outivox.myabc.ui.navigation.mainNavGraph

// TODO 7: Create a navigator for the app and define the navigation graph
@Composable
fun AppNavigator() {
    val navController = LocalNavController.current
    NavHost(
        navController = navController,
        startDestination = SplashScreenDestination,
        enterTransition = { slideInFromStart() },
        exitTransition = { slideOutToEnd() },
        popEnterTransition = { slideInFromEnd() },
        popExitTransition = { slideOutToStart() },
        builder = {
            mainNavGraph()
            authNavGraph()
            // Add other navGraph destinations here
        }
    )
}