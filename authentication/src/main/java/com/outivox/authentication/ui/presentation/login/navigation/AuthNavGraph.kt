package com.outivox.authentication.ui.presentation.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.outivox.authentication.ui.presentation.login.screen.LoginScreen
import com.outivox.core.navigation.LaunchableLoginScreenDestination

// TODO (Step 6.2): In Navigation 3, define authNavGraph on EntryProviderScope<NavKey>:
// 1. Change receiver `NavGraphBuilder.authNavGraph()` to `EntryProviderScope<NavKey>.authNavGraph()`
// 2. Change `composable<LaunchableLoginScreenDestination>` to `entry<LaunchableLoginScreenDestination>`
fun NavGraphBuilder.authNavGraph() {
    composable<LaunchableLoginScreenDestination> {
        LoginScreen()
    }
}