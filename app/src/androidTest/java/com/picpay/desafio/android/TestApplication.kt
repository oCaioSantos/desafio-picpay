package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.di.appModule
import com.picpay.desafio.android.di.instrumentedTestModule
import org.koin.core.context.startKoin

class TestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule, instrumentedTestModule)
        }
    }
}