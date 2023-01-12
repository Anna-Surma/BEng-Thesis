package com.example.inzynierka_app.api

import com.example.inzynierka_app.model.*
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService,
) : ApiHelper {
    override fun login(request: LoginRequest): Call<LoginResponse> = apiService.login(request)

    override suspend fun readData(request: ReadDataRequest): Response<DataResponse> =
        apiService.readData(request)

    override suspend fun writeData(request: WriteDataRequest): Response<DataResponse> =
        apiService.writeData(request)

    override suspend fun readArray(request: ArrayList<ReadDataRequest>): Response<ArrayResponse> =
        apiService.readArray(request)

    override suspend fun writeArray(request: ArrayList<WriteDataRequest>): Response<ArrayResponse> =
        apiService.writeArray(request)

    override suspend fun readCPUMode(request: CPUModeRequest): Response<DataResponse> =
        apiService.readCPUMode(request)

    override suspend fun writeCPUMode(request: WriteCPUModeRequest): Response<DataResponse> =
        apiService.writeCPUMode(request)
}