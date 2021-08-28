package com.zeepy.zeepyforandroid.network

import okhttp3.Interceptor
import okhttp3.Response

class ContentLengthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val contentLength = request.body?.contentLength()
        request.header("Content-Length:${contentLength}")
        return chain.proceed(request)
    }

}