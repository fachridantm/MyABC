package com.outivox.myabc.core.util

import android.os.Build
import androidx.activity.compose.LocalActivity
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun rememberAppManager(): AppManager {
    val activity = LocalActivity.current
    return remember {
        object : AppManager {
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun exitApp() {
                activity?.finishAndRemoveTask()
            }
        }
    }
}
