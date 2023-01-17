package com.example.inzynierka_app.repository

import androidx.lifecycle.LiveData
import com.example.inzynierka_app.api.ApiHelper
import com.example.inzynierka_app.api.SessionManager
import com.example.inzynierka_app.db.ErrorHelper
import com.example.inzynierka_app.db.GripperError
import com.example.inzynierka_app.model.*
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    private val sessionManager: SessionManager,
    private val errorHelper: ErrorHelper
) {
    fun login(request: LoginRequest) =
        apiHelper.login(request)

    suspend fun readData(request: ReadDataRequest) =
        apiHelper.readData(request)

    suspend fun writeData(request: WriteDataRequest) =
        apiHelper.writeData(request)

    suspend fun readArray(request: ArrayList<ReadDataRequest>) =
        apiHelper.readArray(request)

    suspend fun writeArray(request: ArrayList<WriteDataRequest>) =
        apiHelper.writeArray(request)

    suspend fun readCPUMode(request: CPUModeRequest) =
        apiHelper.readCPUMode(request)

    suspend fun writeCPUMode(request: WriteCPUModeRequest) =
        apiHelper.writeCPUMode(request)

    fun saveAuthToken(token: String) =
        sessionManager.saveAuthToken(token)

    fun fetchAuthToken() =
        sessionManager.fetchAuthToken()

    suspend fun insertError(gripperError: GripperError) =
        errorHelper.insertError(gripperError)

    fun getAllErrorsSortedByDate(): LiveData<List<GripperError>> =
        errorHelper.getAllRunsSortedByDate()

    suspend fun deleteAllErrors() =
        errorHelper.deleteAllErrors()
}