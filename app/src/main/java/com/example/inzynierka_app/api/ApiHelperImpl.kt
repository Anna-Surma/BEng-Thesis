package com.example.inzynierka_app.api

import com.example.inzynierka_app.model.*
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService,

): ApiHelper{
    override fun login(request: LoginRequest): Call<LoginResponse> = apiService.login(request)

    override suspend fun readData(request: ReadDataRequest): Response<ReadDataResponse> = apiService.readData(request)

    override suspend fun write_data(request: WriteDataRequest): Response<DataResponse> = apiService.write_data(request)

    override suspend fun readArray(request: ReadArrayRequest): Response<ReadArrayResponse> = apiService.readArray(request)
}