package com.outivox.myabc.core.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun rememberToastManager(): ToastManager {
    return remember {
        object : ToastManager {
            override fun showToast(message: String) {
                // You can implement a custom toast or use a library here.
                println("Toast: $message")
            }
        }
    }
}
