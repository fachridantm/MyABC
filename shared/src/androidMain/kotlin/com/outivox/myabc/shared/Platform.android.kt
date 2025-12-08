package com.outivox.myabc.shared

import android.os.Build

actual fun platform() = "Android ${Build.VERSION.SDK_INT}"