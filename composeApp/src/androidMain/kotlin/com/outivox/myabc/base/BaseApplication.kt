package com.outivox.myabc.base

import android.app.Application
import com.outivox.myabc.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@BaseApplication)
        }
    }
}
