package com.outivox.myabc.core

import android.os.Build

actual fun platform() = "Android ${Build.VERSION.SDK_INT}"
