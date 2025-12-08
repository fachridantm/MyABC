package com.outivox.myabc.util

import androidx.navigation3.runtime.NavKey
import com.outivox.myabc.core.navigation.DashboardScreenDestination
import com.outivox.myabc.core.navigation.LaunchableLoginScreenDestination
import com.outivox.myabc.core.navigation.LaunchableSampleScreenDestination
import com.outivox.myabc.core.navigation.NotificationScreenDestination
import com.outivox.myabc.core.navigation.SettingScreenDestination
import com.outivox.myabc.core.navigation.SplashScreenDestination
import com.outivox.myabc.core.navigation.TransactionScreenDestination
import com.outivox.myabc.ui.presentation.dashboard.bottomsheet.navigation.NavRoute
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

val navKeySerializer = SerializersModule {
    polymorphic(NavKey::class) {
        subclass(LaunchableSampleScreenDestination::class)
        subclass(LaunchableLoginScreenDestination::class)
        subclass(SplashScreenDestination::class)
        subclass(DashboardScreenDestination::class)
        subclass(NotificationScreenDestination::class)
        subclass(TransactionScreenDestination::class)
        subclass(SettingScreenDestination::class)
        subclass(NavRoute.FirstRoute::class)
        subclass(NavRoute.SecondRoute::class)
    }
}
