package com.outivox.myabc.core.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

@Composable
fun <T> debounce(
    value: T,
    delayMillis: Long = 500L,
    onDebounce: (T) -> Unit
) {
    LaunchedEffect(value) {
        delay(delayMillis)
        onDebounce(value)
    }
}

expect class DebounceExecutor(delayMillis: Long) {
    fun execute(action: () -> Unit)
}
