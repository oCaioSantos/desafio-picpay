package com.picpay.desafio.android.common.network.interceptors

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class CacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val cacheControl = CacheControl.Builder()
            .maxAge(1, TimeUnit.HOURS)
            .build()
        val response = chain.proceed(
            request.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build()
        )
        return response.newBuilder()
            .header("Cache-Control", cacheControl.toString())
            .build()
    }
}