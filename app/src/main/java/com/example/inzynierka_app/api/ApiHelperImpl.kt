package com.example.inzynierka_app.api

import com.example.inzynierka_app.ArrayRequestItem
import com.example.inzynierka_app.ArrayResponse
import com.example.inzynierka_app.model.*
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService,
    ) : ApiHelper {
    override fun login(request: LoginRequest): Call<LoginResponse> = apiService.login(request)

    override suspend fun readData(request: ReadDataRequest): Response<ReadDataResponse> =
        apiService.readData(request)

    override suspend fun writeData(request: WriteDataRequest): Response<DataResponse> =
        apiService.writeData(request)

    override suspend fun readArray(request: ArrayList<ArrayRequestItem>): Response<ArrayResponse> =
        apiService.readArray(request)
}