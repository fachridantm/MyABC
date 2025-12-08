package com.outivox.myabc

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration
import com.outivox.myabc.core.navigation.SplashScreenDestination
import com.outivox.myabc.core.theme.MyABCTheme
import com.outivox.myabc.core.util.LocalAppManager
import com.outivox.myabc.core.util.LocalNavBackStack
import com.outivox.myabc.core.util.rememberAppManager
import com.outivox.myabc.di.initKoin
import com.outivox.myabc.navigation.AppNavigator
import com.outivox.myabc.util.navKeySerializer

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
        enforceStrictPlistSanityCheck = false
    }
) {
    val savedStateConfiguration = SavedStateConfiguration {
        serializersModule = navKeySerializer
    }
    val navBackStack = rememberNavBackStack(savedStateConfiguration, SplashScreenDestination)
    MyABCTheme {
        CompositionLocalProvider(
            LocalNavBackStack provides navBackStack,
            LocalAppManager provides rememberAppManager()
        ) {
            AppNavigator()
        }
    }
}
