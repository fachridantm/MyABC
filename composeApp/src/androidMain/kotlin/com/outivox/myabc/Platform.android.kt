package com.outivox.myabc

import android.os.Build

actual fun platform() = "Android ${Build.VERSION.SDK_INT}"