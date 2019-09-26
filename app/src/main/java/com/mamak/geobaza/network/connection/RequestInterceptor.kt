package com.mamak.geobaza.network.connection

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    @Throws
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url()
        val url = originalUrl.newBuilder()
//            .addQueryParameter("api_key", AppConstans.GEO_API_KEY)
            .build()

        val requestBuilder = originalRequest.newBuilder().url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}