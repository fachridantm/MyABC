package com.outivox.myabc.authentication.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.outivox.myabc.authentication.presentation.login.screen.LoginScreen
import com.outivox.myabc.core.navigation.LaunchableLoginScreenDestination

fun EntryProviderScope<NavKey>.authNavGraph() {
    entry<LaunchableLoginScreenDestination> {
        LoginScreen()
    }
}