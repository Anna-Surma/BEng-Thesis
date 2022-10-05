package com.example.inzynierka_app.api

import com.example.inzynierka_app.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST(Constants.LOGIN_URL)
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST(Constants.LOGIN_URL)
    fun readData(@Body request: ReadDataRequest): Call<ReadDataResponse>

    @POST(Constants.LOGIN_URL)
    fun write_data(@Body request: WriteDataRequest): Call<DataResponse>

    @POST(Constants.LOGIN_URL)
    fun readArray(@Body request: ReadArrayRequest): Call<ReadArrayResponse>
}