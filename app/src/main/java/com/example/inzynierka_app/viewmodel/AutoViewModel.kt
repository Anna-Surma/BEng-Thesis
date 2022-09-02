package com.example.inzynierka_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inzynierka_app.api.ApiClient
import com.example.inzynierka_app.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AutoViewModel : ViewModel() {

    private val _readData = MutableLiveData<String>()
    val readData: LiveData<String> = _readData

    private val _writeData = MutableLiveData<Boolean>()
    val writeData: LiveData<Boolean> = _writeData

    private val _controlActive = MutableLiveData<Boolean>()
    val controlActive: LiveData<Boolean> = _controlActive

    private val _autoMode = MutableLiveData<Boolean>()
    val autoMode: LiveData<Boolean> = _autoMode

    private val _cyclesNumber = MutableLiveData<Int>()
    val cyclesNumber: LiveData<Int> = _cyclesNumber

    private val _resetCycles = MutableLiveData<Boolean>()
    val resetCycles: LiveData<Boolean> = _resetCycles

    init{
        _controlActive.value = false
        _autoMode.value = false
    }

    fun readData(read_param: Params) {
        ApiClient().getService()?.readData(ReadDataRequest(1, "2.0", "PlcProgram.Read", read_param))
            ?.enqueue(object : Callback<ReadDataResponse> {
                override fun onFailure(call: Call<ReadDataResponse>, t: Throwable) {
                    Log.i("LoginActivity", t.message.toString())
                }
                override fun onResponse(
                    call: Call<ReadDataResponse>,
                    response: Response<ReadDataResponse>
                ) {
                    val responseBody = response.body()
                    if (response.isSuccessful) {
                        if (responseBody?.result != null) {
                            _readData.value = responseBody.result.toString()
                            Log.i("AutoViewModel", responseBody.result.toString())
                            readData(read_param)
                        }
                    }
                }
            })
    }

     fun write_data(write_param: ParamsWriteVar) {
         ApiClient().getService()
             ?.write_data(WriteDataRequest(1, "2.0", "PlcProgram.Write", write_param))
             ?.enqueue(object : Callback<DataResponse> {
                 override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                     Log.i("LoginActivity", t.message.toString())
                 }

                 override fun onResponse(
                     call: Call<DataResponse>, response: Response<DataResponse>
                 ) {
                     val responseBody = response.body()
                     if (response.isSuccessful) {
                         if (responseBody?.result != null) {
                             _writeData.value = responseBody.result
                         }
                     }
                 }
             })
     }

    fun activeControl(){
        _controlActive.value = true
    }

    fun deactiveControl(){
        _controlActive.value = false
    }

    fun startAuto(){
        _autoMode.value = true
    }

    fun stopAuto(){
        _autoMode.value = false
    }

    fun resetCycles(){
        _resetCycles.value = true
    }
}