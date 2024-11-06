package com.picpay.desafio.android.common.utils

import android.content.Context
import okhttp3.Cache
import java.io.File

fun getCacheSize(sizeMB: Double = 1.0): Double {
    return sizeMB * 1_024 * 1_024
}

fun getCacheDir(context: Context): File {
    return File(context.cacheDir, "http_cache")
}

fun getCache(context: Context): Cache {
    return Cache(
        getCacheDir(context),
        getCacheSize(sizeMB = 10.0).toLong()
    )
}