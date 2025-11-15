package com.outivox.myabc.ui.presentation.dashboard.bottomsheet.navigation

enum class SampleNavRoute {
    FIRST_ROUTE,
    SECOND_ROUTE,
}

sealed class NavRoute(val route: String) {
    data object FirstRoute : NavRoute(SampleNavRoute.FIRST_ROUTE.name)

    data object SecondRoute : NavRoute(SampleNavRoute.SECOND_ROUTE.name)
}
