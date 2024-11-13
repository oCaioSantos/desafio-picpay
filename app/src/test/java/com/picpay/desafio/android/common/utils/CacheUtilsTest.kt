package com.picpay.desafio.android.common.utils

import android.content.Context
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.io.File

@RunWith(RobolectricTestRunner::class)
class CacheUtilsTest {

    private lateinit var context: Context
    private lateinit var cacheDir: File

    @Before
    fun setup() {
        context = RuntimeEnvironment.getApplication()
        cacheDir = File(context.cacheDir, "http_cache")
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
    }

    @After
    fun tearDown() {
        if (cacheDir.exists()) {
            cacheDir.deleteRecursively()
        }
    }

    @Test
    fun `getCacheSize should return correct size in bytes`() {
        // given
        // when
        // then
        Assert.assertEquals(2_097_152.0, getCacheSize(sizeMB = 2.0), 0.0)
        Assert.assertEquals(10_485_760.0, getCacheSize(sizeMB = 10.0), 0.0)
        Assert.assertEquals(20_971_520.0, getCacheSize(sizeMB = 20.0), 0.0)
    }

    @Test
    fun `getCacheDir should return correct cache directory`() {
        // given
        val expectedDir = cacheDir

        // when
        val result = getCacheDir(context)

        // then
        Assert.assertEquals(expectedDir.path, result.path)
    }

    @Test
    fun `getCache should return Cache with correct directory and size`() {
        // given
        val expectedSize = getCacheSize(sizeMB = 10.0).toLong()
        val expectedDir = cacheDir

        // when
        val cache = getCache(context)

        // then
        Assert.assertEquals(expectedSize, cache.maxSize())
        Assert.assertEquals(expectedDir.path, cache.directory.path)
    }

}