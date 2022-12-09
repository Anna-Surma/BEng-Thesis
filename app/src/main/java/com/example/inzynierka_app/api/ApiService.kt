package com.example.inzynierka_app.api

import com.example.inzynierka_app.ArrayRequestItem
import com.example.inzynierka_app.ArrayResponse
import com.example.inzynierka_app.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST(Constants.LOGIN_URL)
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST(Constants.LOGIN_URL)
    suspend fun readData(@Body request: ReadDataRequest): Response<ReadDataResponse>

    @POST(Constants.LOGIN_URL)
    suspend fun writeData(@Body request: WriteDataRequest): Response<DataResponse>

    @POST(Constants.LOGIN_URL)
    suspend fun readArray(@Body request: ArrayList<ArrayRequestItem>): Response<ArrayResponse>

    @POST(Constants.LOGIN_URL)
    suspend fun readCPUMode(@Body request: CPUModeRequest): Response<ReadDataResponse>
}