package com.outivox.myabc.core.deeplink

import com.outivox.myabc.core.navigation.NavigationDestination

data class Deeplink(
    val uri: String,
    val destination: NavigationDestination?,
    val accessLevel: DeeplinkAccessLevel,
    val viewLevel: DeeplinkViewLevel,
)

enum class DeeplinkAccessLevel {
    PRE_LOGIN,
    POST_LOGIN,
    INVALID,
}

enum class DeeplinkStartState {
    COLD_START,
    WARM_HOT_START,
}

enum class DeeplinkViewLevel {
    SCREEN,
    BOTTOM_SHEET,
}