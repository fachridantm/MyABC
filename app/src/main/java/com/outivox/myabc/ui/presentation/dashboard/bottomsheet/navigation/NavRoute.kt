package com.outivox.myabc.ui.presentation.dashboard.bottomsheet.navigation

// TODO (Step 8.1): In Navigation 3, define bottom sheet routes as NavKey:
// 1. Remove enum class SampleNavRoute
// 2. Make `sealed class NavRoute : NavKey` (import androidx.navigation3.runtime.NavKey)
// 3. Annotate destinations with @Serializable:
//    @Serializable
//    data object FirstRoute : NavRoute()
//    @Serializable
//    data object SecondRoute : NavRoute()
enum class SampleNavRoute {
    FIRST_ROUTE,
    SECOND_ROUTE,
}

sealed class NavRoute(val route: String) {
    data object FirstRoute : NavRoute(SampleNavRoute.FIRST_ROUTE.name)

    data object SecondRoute : NavRoute(SampleNavRoute.SECOND_ROUTE.name)
}

