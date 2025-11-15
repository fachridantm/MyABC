package com.outivox.core.util

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

// TODO 6: Create a local provider for navController
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
