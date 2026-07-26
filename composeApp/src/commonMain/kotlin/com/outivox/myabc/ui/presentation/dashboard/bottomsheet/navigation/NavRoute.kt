package com.outivox.myabc.ui.presentation.dashboard.bottomsheet.navigation

import com.outivox.myabc.core.navigation.NavigationDestination
import kotlinx.serialization.Serializable

@Serializable
sealed class NavRoute : NavigationDestination() {
    @Serializable
    data object FirstRoute : NavRoute()

    @Serializable
    data object SecondRoute : NavRoute()

    @Serializable
    data object ThirdRoute : NavRoute()

    @Serializable
    data object FourthRoute : NavRoute()
}
