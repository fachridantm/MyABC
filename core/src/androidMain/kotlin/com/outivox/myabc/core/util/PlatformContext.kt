package com.outivox.myabc.core.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

/**
 * A holder for platform-specific context.
 * On Android, this will be the Application Context.
 * On other platforms, it can be null or a different object.
 */
actual typealias PlatformContext = Context

@Composable
actual fun rememberPlatformContext(): PlatformContext {
    val applicationContext = LocalContext.current.applicationContext
    return remember { applicationContext }
}
