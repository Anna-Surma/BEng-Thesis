package com.example.inzynierka_app.api

import com.example.inzynierka_app.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST(Constants.ENDPOINT_URL)
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST(Constants.ENDPOINT_URL)
    suspend fun readData(@Body request: ReadDataRequest): Response<DataResponse>

    @POST(Constants.ENDPOINT_URL)
    suspend fun writeData(@Body request: WriteDataRequest): Response<DataResponse>

    @POST(Constants.ENDPOINT_URL)
    suspend fun readArray(@Body request: ArrayList<ReadDataRequest>): Response<ArrayResponse>

    @POST(Constants.ENDPOINT_URL)
    suspend fun writeArray(@Body request: ArrayList<WriteDataRequest>): Response<ArrayResponse>

    @POST(Constants.ENDPOINT_URL)
    suspend fun readCPUMode(@Body request: CPUModeRequest): Response<DataResponse>

    @POST(Constants.ENDPOINT_URL)
    suspend fun writeCPUMode(@Body request: WriteCPUModeRequest): Response<DataResponse>
}
