package com.example.inzynierka_app.api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        sessionManager.fetchAuthToken()?.let {
            requestBuilder
                .addHeader("X-Auth-Token", it)
            // .addHeader("Connection", "close")
        }
        return chain.proceed(requestBuilder.build())
    }
}