package com.example.inzynierka_app.api

import com.example.inzynierka_app.model.Constants
import com.example.inzynierka_app.model.LoginRequest
import com.example.inzynierka_app.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST(Constants.LOGIN_URL)
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}