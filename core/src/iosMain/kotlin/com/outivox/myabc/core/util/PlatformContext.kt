package com.outivox.myabc.core.util

import androidx.compose.runtime.Composable

/**
 * A holder for platform-specific context.
 * On Android, this will be the Application Context.
 * On other platforms, it can be null or a different object.
 */
actual abstract class PlatformContext

@Composable
actual fun rememberPlatformContext(): PlatformContext {
    return object : PlatformContext() {}
}
