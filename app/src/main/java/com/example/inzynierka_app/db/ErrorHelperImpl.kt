package com.example.inzynierka_app.db

import androidx.lifecycle.LiveData
import javax.inject.Inject

class ErrorHelperImpl @Inject constructor(
    private val errorDao: ErrorDao
) : ErrorHelper {
    override suspend fun insert(gripperError: GripperError) = errorDao.insert(gripperError)

    override fun getAllRunsSortedByDate(): LiveData<List<GripperError>> =
        errorDao.getAllErrorsSortedByDate()

    override suspend fun delete() = errorDao.delete()
}