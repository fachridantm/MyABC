package com.outivox.myabc.core.deeplink

internal val getPreLoginLevel: List<String> = listOf(
    DeeplinkConstant.PLAYGROUND_SCREEN_DEEPLINK,
)

internal val getPostLoginLevel: List<String> = listOf(
    DeeplinkConstant.PLAYGROUND_SCREEN_DEEPLINK,
)

internal fun String.isPreLoginLevelValid(): Boolean = getPreLoginLevel.find { this.contains(it) } != null

internal fun String.isPostLoginLevelValid(): Boolean = getPostLoginLevel.find { this.contains(it) } != null