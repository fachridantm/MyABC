package com.outivox.myabc.core.util

/**
 * Searches the query string for the first value with the given key on Android.
 */
actual fun PlatformUri.getUriQueryParameter(key: String): String? {
    return query?.split("&")?.find { it.startsWith("$key=") }?.substringAfter("$key=")
}
