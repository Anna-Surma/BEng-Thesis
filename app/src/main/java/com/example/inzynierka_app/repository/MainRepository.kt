package com.example.inzynierka_app.repository

import com.example.inzynierka_app.api.ApiHelper
import com.example.inzynierka_app.api.SessionManager
import com.example.inzynierka_app.model.*
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val sessionManager: SessionManager
) {
    fun login(request: LoginRequest) = apiHelper.login(request)

    suspend fun readData(request: ReadDataRequest) = apiHelper.readData(request)

    suspend fun write_data(request: WriteDataRequest) = apiHelper.write_data(request)

    suspend fun readArray(request: ReadArrayRequest) = apiHelper.readArray(request)

    fun saveAuthToken(token: String) = sessionManager.saveAuthToken(token)

    fun fetchAuthToken() = sessionManager.fetchAuthToken()
}