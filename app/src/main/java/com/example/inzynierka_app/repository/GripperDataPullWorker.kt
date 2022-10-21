package com.example.inzynierka_app.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.inzynierka_app.model.Params
import com.example.inzynierka_app.model.ReadDataRequest
import javax.inject.Inject

// Synchronization
class GripperDataPullWorker @Inject constructor(
    private val mainRepository: MainRepository
) {
    var synchronizing = false
    var cycles = MutableLiveData("null")

    suspend fun startReadCycles(read_param: Params) {
        Log.i("GripperViewModelData", cycles.value.toString())
        try {
            val response =
                mainRepository.readData(ReadDataRequest(1, "2.0", "PlcProgram.Read", read_param))
            val responseBody = response.body()
            if (response.isSuccessful) {
                if (responseBody?.result != null) {
                    if (cycles.value != responseBody.result.toString())
                        cycles.value = responseBody.result.toString()
                }
            }
        } catch (e: Exception) {
            synchronizing = false
        }
    }

    fun stopReadCycles() {
        synchronizing = false
    }


}