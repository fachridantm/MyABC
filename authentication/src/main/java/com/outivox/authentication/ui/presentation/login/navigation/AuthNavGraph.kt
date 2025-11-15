package com.outivox.authentication.ui.presentation.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.outivox.authentication.ui.presentation.login.screen.LoginScreen
import com.outivox.core.navigation.LaunchableLoginScreenDestination

fun NavGraphBuilder.authNavGraph() {
    composable<LaunchableLoginScreenDestination> {
        LoginScreen()
    }
}