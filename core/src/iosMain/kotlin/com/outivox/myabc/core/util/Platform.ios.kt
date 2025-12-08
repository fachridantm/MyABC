package com.outivox.myabc.core.util

import platform.Foundation.NSLog
import platform.Foundation.NSURL
import platform.UIKit.UIDevice

actual fun platform() = UIDevice.currentDevice.systemName + " " + UIDevice.currentDevice.systemVersion

actual object PrintLog {
    actual fun e(tag: String, message: String, throwable: Throwable?) {
        if (throwable != null) {
            NSLog("ERROR: [$tag] $message. Throwable: $throwable CAUSE ${throwable.cause}")
        } else {
            NSLog("ERROR: [$tag] $message")
        }
    }

    actual fun d(tag: String, message: String) {
        NSLog("DEBUG: [$tag] $message")
    }

    actual fun i(tag: String, message: String) {
        NSLog("INFO: [$tag] $message")
    }
}

/**
 * On iOS, [com.outivox.myabc.core.util.PlatformUri] is a typealias for [platform.Foundation.NSURL].
 */
actual typealias PlatformUri = NSURL

/**
 * Parses a string into a [com.outivox.myabc.core.util.PlatformUri] on iOS.
 */
actual fun String.toPlatformUri(): PlatformUri? {
    return NSURL.URLWithString(this)
}


