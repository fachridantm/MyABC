package com.outivox.myabc.authentication.util

import android.os.Build

actual fun platform() = PlatformComponent("Android ${Build.VERSION.SDK_INT}")
