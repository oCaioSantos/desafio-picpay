package com.picpay.desafio.android.di

import com.picpay.desafio.android.common.network.interceptors.NetworkLoggingInterceptor
import com.picpay.desafio.android.domain.service.PicPayService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val instrumentedTestModule = module {

    single<OkHttpClient> {
        OkHttpClient
            .Builder()
            .addInterceptor(NetworkLoggingInterceptor())
            .build()
    }

    single<PicPayService> {
        Retrofit.Builder()
            .baseUrl("http://localhost:8080/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(PicPayService::class.java)
    }
}