package com.example.inzynierka_app.repository

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

    private var arrayErrorResponse = ArrayList<ArrayResponseItem>()
    var arrayErrorResponseLiveData = MutableLiveData<ArrayList<ArrayResponseItem>>()

    private var stepsArrayResponse = ArrayList<ArrayResponseItem>()
    var stepsArrayResponseLiveData = MutableLiveData<ArrayList<ArrayResponseItem>>()

    var CPUmode = MutableLiveData<String>()

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
            arrayErrorResponse.clear()
            val response =
                mainRepository.readArray(read_array_item)
            val responseBody = response.body()
            if (response.isSuccessful) {
                if (responseBody != null) {
                    for (arrayResponseItem in responseBody) {
                        arrayErrorResponse.add(arrayResponseItem)
                        arrayErrorResponseLiveData.value = arrayErrorResponse
                    }
                }
            }
        } catch (e: Exception) {
            //Catch error
        }
    }

    suspend fun readSteps(read_array_item: ArrayList<ArrayRequestItem>) {
        try {
            stepsArrayResponse.clear()
            val response =
                mainRepository.readArray(read_array_item)
            val responseBody = response.body()
            if (response.isSuccessful) {
                if (responseBody != null) {
                    for (stepsArrayResponseItem in responseBody) {
                        stepsArrayResponse.add(stepsArrayResponseItem)
                        stepsArrayResponseLiveData.value = stepsArrayResponse
                    }
                }
            }
        } catch (e: Exception) {
            //Catch error
        }
    }

    suspend fun readMode(read_param: Params) {
        try {
            val response =
                mainRepository.readData(ReadDataRequest(1, "2.0", "PlcProgram.Read", read_param))
            val responseBody = response.body()
            if (response.isSuccessful) {
                if (responseBody?.result != null) {
                    if (CPUmode.value != responseBody.result)
                    {
                        CPUmode.value = responseBody.result.toString()
                    }
                }
            }
        } catch (e: Exception) {
            //Catch error
        }
    }
}