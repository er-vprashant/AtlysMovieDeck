package com.prashant.atlysmoviedeck.data.remote

import com.prashant.atlysmoviedeck.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val apiKey = BuildConfig.TMDB_API_KEY

        val requestBuilder = original.newBuilder()

        val newUrl = when {
            apiKey.isBlank() -> original.url
            apiKey.startsWith("Bearer ", ignoreCase = true) -> {
                requestBuilder.header("Authorization", apiKey)
                original.url
            }
            else -> {
                original.url.newBuilder()
                    .addQueryParameter("api_key", apiKey)
                    .build()
            }
        }

        val newRequest = requestBuilder
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}
