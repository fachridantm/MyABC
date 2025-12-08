package com.outivox.myabc.core.util

actual class DebounceExecutor actual constructor(private val delayMillis: Long) {
    actual fun execute(action: () -> Unit) {
        action()
    }
}
