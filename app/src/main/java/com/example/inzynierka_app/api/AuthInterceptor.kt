package com.example.inzynierka_app.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
): Interceptor {

    //TODO Why interceptor is call during all request? is that correct?
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        sessionManager.fetchAuthToken()?.let {
            requestBuilder
                .addHeader("X-Auth-Token", it)
            Log.i("LoginAuth", "Bearer $it")
        }
        return chain.proceed(requestBuilder.build())
    }
}