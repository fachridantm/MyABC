package com.outivox.myabc.di

import com.outivox.myabc.core.navigation.NavigationViewModel
import com.outivox.myabc.ui.presentation.dashboard.DashboardViewModel
import com.outivox.myabc.ui.presentation.splash.SplashViewModel
import com.outivox.myabc.util.PlatformComponent
import com.outivox.myabc.util.platform
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.koin.mp.KoinPlatform

val appModule = module {
    // Add your ViewModels and other dependencies here
    factory { platform() }
    factory { SplashViewModel() }
    factory { NavigationViewModel() }
    factory { DashboardViewModel() }
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(appModule)
    }

    val platformInfo = KoinPlatform.getKoin().get<PlatformComponent>().name
    println("Running on: $platformInfo")
}

// You can add platform-specific modules here
expect fun platformModule(): Module
