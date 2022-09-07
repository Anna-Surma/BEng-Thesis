package com.example.inzynierka_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inzynierka_app.api.ApiClient
import com.example.inzynierka_app.model.DataResponse
import com.example.inzynierka_app.model.ParamsWriteVar
import com.example.inzynierka_app.model.WriteDataRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManualViewModel : ViewModel(){

    private val _manualMode = MutableLiveData<Boolean>()
    val manualMode: LiveData<Boolean> = _manualMode

    private val _writeData = MutableLiveData<Boolean>()
    val writeData: LiveData<Boolean> = _writeData

    init {
        _manualMode.value = false
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

    fun startStep(){
        _manualMode.value = true
    }

    fun stopStep(){
        _manualMode.value = false
    }
}