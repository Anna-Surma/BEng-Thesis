package com.example.inzynierka_app.db

import androidx.lifecycle.LiveData

interface ErrorHelper {

    suspend fun insertError(gripperError: GripperError)
    fun getAllRunsSortedByDate(): LiveData<List<GripperError>>
    suspend fun deleteAllErrors()

}