package com.example.inzynierka_app.api

import android.content.Context
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    fun initialize(context: Context): ApiService {
        Log.i("LoginINITIAL", "INITIAL")
        if (apiService == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient(context))
                .build()
            apiService = retrofit.create(ApiService::class.java)
        }
        return apiService!!
    }


    fun getService() = apiService

    private fun okhttpClient(context: Context): OkHttpClient {
        val logger: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        Log.i("LoginOkHttp", "OkHttp")
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .addInterceptor(logger)
            .build()
    }

    companion object{
        var apiService: ApiService? = null
    }
}

