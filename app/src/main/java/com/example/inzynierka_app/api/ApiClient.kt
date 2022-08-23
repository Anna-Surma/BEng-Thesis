package com.example.inzynierka_app.api

import android.content.Context
import android.util.Log
import com.example.inzynierka_app.model.AuthInterceptor
import com.example.inzynierka_app.model.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    fun initialize(context: Context): ApiService {
        Log.i("LoginINITIAL", "INITIAL")
       // if (apiService == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient(context))
                .build()
            apiService = retrofit.create(ApiService::class.java)
      //  }

        return apiService!!
    }

    fun getService(context: Context):ApiService{
        okhttpClient(context)
        initialize(context)

        return apiService!!
    }

    private fun okhttpClient(context: Context): OkHttpClient {
        Log.i("LoginOkHttp", "OkHttp")
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()
    }

    companion object{
        var apiService: ApiService? = null
    }
}

