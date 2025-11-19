package com.outivox.core.deeplink

import com.outivox.core.navigation.LaunchableSampleScreenDestination
import com.outivox.core.navigation.NavigationDestination

internal fun String?.toPreLoginDestination(): NavigationDestination? {
    if (this == null) return null
    return when {
        this.contains(DeeplinkConstant.PLAYGROUND_SCREEN_DEEPLINK) -> {
            LaunchableSampleScreenDestination
        }

        else -> {
            null
        }
    }
}

internal fun String?.toPostLoginDestination(): NavigationDestination? {
    if (this == null) return null
    return when {
        this.contains(DeeplinkConstant.PLAYGROUND_SCREEN_DEEPLINK) -> {
            LaunchableSampleScreenDestination
        }

        else -> {
            null
        }
    }
}