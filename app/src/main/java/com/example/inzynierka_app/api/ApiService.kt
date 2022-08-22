package com.example.inzynierka_app.api

import com.example.inzynierka_app.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST(Constants.LOGIN_URL)
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET(Constants.LOGIN_URL)
    fun fetchData(): Call<DataResponse>

    @POST(Constants.LOGIN_URL)
    fun read_data(@Body request: ReadDataRequest): Call<DataResponse>

    @POST(Constants.LOGIN_URL)
    fun write_data(@Body request: WriteDataRequest): Call<DataResponse>
}