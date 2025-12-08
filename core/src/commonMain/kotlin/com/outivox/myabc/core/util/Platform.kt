package com.outivox.myabc.core.util

expect fun platform(): String

expect object PrintLog {
    fun e(tag: String, message: String, throwable: Throwable? = null)
    fun d(tag: String, message: String)
    fun i(tag: String, message: String)
}

/**
 * A platform-agnostic representation of a URI.
 *
 * On Android, this will be an `actual typealias` to `android.net.Uri`.
 * On iOS, this will be an `actual typealias` to `platform.Foundation.NSURL`.
 */
expect class PlatformUri

/**
 * Parses a string into a [PlatformUri].
 * Returns null if the parsing fails.
 */
expect fun String.toPlatformUri(): PlatformUri?