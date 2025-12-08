package com.outivox.myabc.core.util

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

val LocalNavController by lazy {
    staticCompositionLocalOf<NavHostController> {
        error("No NavHostController provided")
    }
}

val LocalBottomSheetNavController by lazy {
    staticCompositionLocalOf<NavHostController> {
        error("No NavHostController provided")
    }
}

val LocalNavBackStack by lazy {
    staticCompositionLocalOf<NavBackStack<NavKey>> {
        error("No NavBackStack provided")
    }
}

val LocalBottomSheetNavBackStack by lazy {
    staticCompositionLocalOf<NavBackStack<NavKey>> {
        error("No NavBackStack provided")
    }
}
