package com.example.cityairqualityapp.util

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class MyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
            .newBuilder()
            .addHeader(
                "x-api-key",
                Constants.API_KEY
            )
            .addHeader(
                "Content-type",
                "application/json"
            )
            .build()

        return chain.proceed(request)
    }
}