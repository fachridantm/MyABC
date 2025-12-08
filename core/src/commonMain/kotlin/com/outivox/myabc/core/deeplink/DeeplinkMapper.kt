package com.outivox.myabc.core.deeplink

import com.outivox.myabc.core.navigation.LaunchableSampleScreenDestination
import com.outivox.myabc.core.navigation.NavigationDestination

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