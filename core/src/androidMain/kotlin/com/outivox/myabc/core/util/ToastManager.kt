package com.outivox.myabc.core.util

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun rememberToastManager(): ToastManager {
    val context = rememberPlatformContext()
    return remember {
        object : ToastManager {
            override fun showToast(message: String) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
