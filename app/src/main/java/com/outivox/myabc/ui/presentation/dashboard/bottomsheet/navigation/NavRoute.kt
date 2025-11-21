package com.outivox.myabc.ui.presentation.dashboard.bottomsheet.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class NavRoute : NavKey {
    @Serializable
    data object FirstRoute : NavRoute()

    @Serializable
    data object SecondRoute : NavRoute()
}
