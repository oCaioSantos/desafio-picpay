package com.picpay.desafio.android.common.network.interceptors

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class CacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val cacheControl = CacheControl.Builder()
            .maxAge(1, TimeUnit.HOURS)
            .build()
        with(cacheControl) {
            val response = chain.proceed(
                chain.request()
                    .newBuilder()
                    .header("Cache-Control", this.toString())
                    .build()
            )
            return response.newBuilder()
                .header("Cache-Control", this.toString())
                .build()
        }
    }
}