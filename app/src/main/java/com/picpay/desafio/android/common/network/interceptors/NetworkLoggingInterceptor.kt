package com.picpay.desafio.android.common.network.interceptors

import okhttp3.logging.HttpLoggingInterceptor

object NetworkLoggingInterceptor {
    operator fun invoke(level: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(level)
        }
    }
}