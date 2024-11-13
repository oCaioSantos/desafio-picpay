package com.picpay.desafio.android.common.network.interceptors

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.check
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class CacheInterceptorTest {

    private lateinit var interceptor: CacheInterceptor

    @Mock
    private lateinit var chain: Interceptor.Chain

    @Before
    fun setup() {
        interceptor = CacheInterceptor()
    }

    @Test
    fun `intercept should add Cache-Control header to request and response`() {
        // given
        val request = Request.Builder()
            .url("https://exemplo/api")
            .build()

        val expectedCacheControl = CacheControl.Builder()
            .maxAge(1, TimeUnit.HOURS)
            .build()

        whenever(chain.request()).thenReturn(request)
        whenever(chain.proceed(any())).thenAnswer { invocation ->
            val interceptedRequest = invocation.arguments[0] as Request
            Response.Builder()
                .request(interceptedRequest)
                .protocol(Protocol.HTTP_1_1)
                .code(200)
                .message("OK")
                .body(null)
                .build()
        }

        // when
        val response = interceptor.intercept(chain)

        // then
        verify(chain).proceed(check {
            Assert.assertEquals(expectedCacheControl.toString(), it.header("Cache-Control"))
        })
        Assert.assertEquals(expectedCacheControl.toString(), response.header("Cache-Control"))
    }
}
