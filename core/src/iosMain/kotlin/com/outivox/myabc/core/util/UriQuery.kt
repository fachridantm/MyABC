package com.outivox.myabc.core.util

import platform.Foundation.NSURLComponents

/**
 * Searches the query string for the first value with the given key on iOS.
 */
actual fun PlatformUri.getUriQueryParameter(key: String): String? {
    val components = NSURLComponents.componentsWithURL(this, false)
    return components?.queryItems?.firstOrNull().toString()
}
