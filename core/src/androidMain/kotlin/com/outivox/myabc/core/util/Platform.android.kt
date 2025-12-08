package com.outivox.myabc.core.util

import android.os.Build
import android.util.Log
import java.net.URI

actual fun platform() = "Android ${Build.VERSION.SDK_INT}"

actual object PrintLog {
    actual fun e(tag: String, message: String, throwable: Throwable?) {
        if (throwable != null) {
            Log.e(tag, message, throwable)
        } else {
            Log.e(tag, message)
        }
    }

    actual fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    actual fun i(tag: String, message: String) {
        Log.i(tag, message)
    }
}

/**
 * On Android, [com.outivox.myabc.core.util.PlatformUri] is a wrapper around [android.net.Uri].
 */
actual typealias PlatformUri = URI
/**
 * Parses a string into a [com.outivox.myabc.core.util.PlatformUri] on Android.
 */
actual fun String.toPlatformUri(): PlatformUri? = runCatching {
    URI.create(this)
}.getOrNull()