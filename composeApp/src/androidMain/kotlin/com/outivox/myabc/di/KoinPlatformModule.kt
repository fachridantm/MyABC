@file:JvmName("KoinPlatformModuleAndroid")

package com.outivox.myabc.di

import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    // Add Android-specific dependencies here
}
