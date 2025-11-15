package com.outivox.core.deeplink

import android.net.Uri
import com.outivox.core.navigation.NavigationDestination

data class Deeplink(
    val uri: Uri,
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