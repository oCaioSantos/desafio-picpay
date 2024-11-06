package com.picpay.desafio.android.application

import android.app.Application
import com.picpay.desafio.android.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Picpay : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Picpay)
            modules(appModule)
        }
    }

}