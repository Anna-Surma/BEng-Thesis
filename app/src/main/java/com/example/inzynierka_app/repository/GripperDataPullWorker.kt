package com.example.inzynierka_app.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.inzynierka_app.ArrayRequestItem
import com.example.inzynierka_app.ArrayResponseItem
import com.example.inzynierka_app.model.Params
import com.example.inzynierka_app.model.ReadDataRequest
import javax.inject.Inject

// Synchronization
class GripperDataPullWorker @Inject constructor(
    private val mainRepository: MainRepository
) {
    var synchronizing = false
    var cycles = MutableLiveData<String>()

    var cyclesTime = MutableLiveData<String>()

    private var arrayResponse = ArrayList<ArrayResponseItem>()
    var arrayResponseLiveData = MutableLiveData<ArrayList<ArrayResponseItem>>()

    suspend fun startReadCycles(read_param: Params) {
        try {
            val response =
                mainRepository.readData(ReadDataRequest(1, "2.0", "PlcProgram.Read", read_param))
            val responseBody = response.body()
            if (response.isSuccessful) {
                if (responseBody?.result != null) {
                    if (cycles.value != responseBody.result)
                    {
                        cycles.value = responseBody.result.toString().dropLast(2)
                    }
                }
            }
        } catch (e: Exception) {
            synchronizing = false
        }
    }

    fun stopReadCycles() {
        synchronizing = false
    }

    suspend fun startReadCyclesTime(read_param: Params) {
        try {
            val response =
                mainRepository.readData(ReadDataRequest(1, "2.0", "PlcProgram.Read", read_param))
            val responseBody = response.body()
            if (response.isSuccessful) {
                if (responseBody?.result != null) {
                    if (cyclesTime.value != responseBody.result)
                    {
                        cyclesTime.value = responseBody.result.toString().dropLast(2)
                    }
                }
            }
        } catch (e: Exception) {
            synchronizing = false
        }
    }

    suspend fun readErrors(read_array_item: ArrayList<ArrayRequestItem>) {
        try {
            arrayResponse.clear()
            val response =
                mainRepository.readArray(read_array_item)
            val responseBody = response.body()
            if (response.isSuccessful) {
                if (responseBody != null) {
                    for (arrayResponseItem in responseBody) {
                        arrayResponse.add(arrayResponseItem)
                        arrayResponseLiveData.value = arrayResponse
                    }
                }
            }
        } catch (e: Exception) {
            //Catch error
        }
    }
}