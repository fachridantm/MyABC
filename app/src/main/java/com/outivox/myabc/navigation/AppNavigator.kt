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

// TODO (Step 5.3): In Navigation 3, NavHost is replaced by NavDisplay:
// 1. Obtain navBackStack from LocalNavBackStack.current
// 2. Call NavDisplay(
//        backStack = navBackStack,
//        entryDecorators = listOf(
//            rememberSaveableStateHolderNavEntryDecorator(),
//            rememberViewModelStoreNavEntryDecorator(),
//        ),
//        onBack = { navBackStack.removeLastOrNull() },
//        entryProvider = entryProvider {
//            mainNavGraph()
//            authNavGraph()
//        },
//        transitionSpec = { slideInFromEnd() togetherWith ExitTransition.KeepUntilTransitionsFinished },
//        popTransitionSpec = { EnterTransition.None togetherWith slideOutToStart() },
//        predictivePopTransitionSpec = { EnterTransition.None togetherWith slideOutToStart() },
//    )
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