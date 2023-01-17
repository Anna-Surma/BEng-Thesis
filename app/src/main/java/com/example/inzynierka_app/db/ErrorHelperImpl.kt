package com.example.inzynierka_app.db

import androidx.lifecycle.LiveData
import javax.inject.Inject

class ErrorHelperImpl @Inject constructor(
    private val errorDao: ErrorDao
) : ErrorHelper {
    override suspend fun insertError(gripperError: GripperError) =
        errorDao.insertError(gripperError)

    override fun getAllRunsSortedByDate(): LiveData<List<GripperError>> =
        errorDao.getAllErrorsSortedByDate()

    override suspend fun deleteAllErrors() =
        errorDao.deleteAllErrors()
}