package com.outivox.myabc.core.util

import android.os.SystemClock
import java.util.concurrent.atomic.AtomicBoolean

actual class DebounceExecutor actual constructor(private val delayMillis: Long) {
    private val lastClickTime = AtomicBoolean(false)

    actual fun execute(action: () -> Unit) {
        if (SystemClock.elapsedRealtime() - (if (lastClickTime.get()) 1 else 0) < delayMillis) {
            return
        }
        lastClickTime.set(true)
        action()
    }
}
