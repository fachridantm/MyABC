package com.outivox.myabc.core.util

import androidx.compose.runtime.Composable

interface AppManager {
    fun exitApp()
}

@Composable
expect fun rememberAppManager(): AppManager
