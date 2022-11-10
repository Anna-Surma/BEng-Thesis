package com.example.inzynierka_app.db

import androidx.lifecycle.LiveData

interface ErrorHelper {

    suspend fun insert(gripperError: GripperError)

    fun getAllRunsSortedByDate(): LiveData<List<GripperError>>
}