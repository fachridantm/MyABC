package com.outivox.authentication.ui.presentation.login.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.outivox.authentication.ui.presentation.login.screen.LoginScreen
import com.outivox.core.navigation.LaunchableLoginScreenDestination

fun EntryProviderScope<NavKey>.authNavGraph() {
    entry<LaunchableLoginScreenDestination> {
        LoginScreen()
    }
}