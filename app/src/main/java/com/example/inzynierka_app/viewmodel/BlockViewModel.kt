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

class BlockViewModel : ViewModel() {

    private val _rightDir = MutableLiveData<Boolean>()
    val rightDir: LiveData<Boolean> = _rightDir

    private val _readData = MutableLiveData<ArrayList<ReadDataRequest>>()
    val readData: LiveData<ArrayList<ReadDataRequest>> = _readData

    fun readData(array: ArrayList<ReadDataRequest>) {
        ApiClient().getService()?.readArray(ReadArrayRequest(array))
            ?.enqueue(object : Callback<ReadArrayResponse> {
                override fun onFailure(call: Call<ReadArrayResponse>, t: Throwable) {
                    Log.i("BlockActivity", t.message.toString())
                }
                override fun onResponse(
                    call: Call<ReadArrayResponse>,
                    response: Response<ReadArrayResponse>
                ) {
                    val responseBody = response.body()
                    Log.i("BlockViewModel", responseBody?.araayRes.toString())
                    if (response.isSuccessful) {
                        if (responseBody?.araayRes != null) {
                            _readData.value = responseBody.araayRes
                            Log.i("BlockViewModel", responseBody.araayRes.toString())
                            readData(array)
                        }
                    }
                }
            })
    }


    fun rightDirClick(){
        _rightDir.value = true
    }

    fun rightDirRelease(){
        _rightDir.value = false
    }

//    fun rightMove(){
//        when ()
//    }
}