package com.outivox.myabc.core.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.posix.exit

@Composable
actual fun rememberAppManager(): AppManager {
    return remember {
        object : AppManager {
            override fun exitApp() {
                exit(0) // Use with caution due to Apple's guidelines
            }
        }
    }
}
