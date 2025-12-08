package com.outivox.myabc.core.util

import androidx.compose.runtime.Composable

/**
 * An interface for showing toast messages.
 */
interface ToastManager {
    fun showToast(message: String)
}

@Composable
expect fun rememberToastManager(): ToastManager
