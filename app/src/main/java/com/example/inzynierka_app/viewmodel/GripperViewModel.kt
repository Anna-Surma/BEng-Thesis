package com.example.inzynierka_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inzynierka_app.model.*
import com.example.inzynierka_app.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GripperViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

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

    private val _manualMode = MutableLiveData<Boolean>()
    val manualMode: LiveData<Boolean> = _manualMode

    private val _readDataArray = MutableLiveData<ArrayList<ReadDataRequest>>()
    val readDataArray: LiveData<ArrayList<ReadDataRequest>> = _readDataArray

    private val _isRunning = MutableLiveData<Boolean>()
    val isRunning: LiveData<Boolean> = _isRunning

    init {
        _controlActive.value = false
        _autoMode.value = false
        _manualMode.value = false
        _isRunning.value = false
    }

    //What with onFailure???
    fun readData(read_param: Params) = viewModelScope.launch {
        //TODO Change while(true), should work only when is in Auto Fragment
        while (true) {
            delay(100)
            val response =
                mainRepository.readData(ReadDataRequest(1, "2.0", "PlcProgram.Read", read_param))
            val responseBody = response.body()
            if (response.isSuccessful) {
                if (responseBody?.result != null) {
                    _readData.value = responseBody.result.toString()
                    Log.i("AutoViewModel", "READ" + responseBody.result.toString())
                }
            }
        }
    }

    fun writeData(write_param: ParamsWriteVar) = viewModelScope.launch {
        val response =
            mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", write_param))
        val responseBody = response.body()
        if (response.isSuccessful) {
            Log.i("AutoViewModel", "RESULT= " + responseBody?.result.toString()+ write_param.`var`+write_param.value)
            if (responseBody?.result != null) {
                _writeData.value = responseBody.result
            }
        }
    }

//    fun readData(array: ArrayList<ReadDataRequest>) = viewModelScope.launch {
//        val response = mainRepository.readArray(ReadArrayRequest(array))
//        val responseBody = response.body()
//        Log.i("BlockViewModel", responseBody?.arrayRes.toString())
//        if (response.isSuccessful) {
//            if (responseBody?.arrayRes != null) {
//                _readDataArray.value = responseBody.arrayRes
//                Log.i("BlockViewModel", responseBody.arrayRes.toString())
//                //  readData(array)
//            }
//        }
//    }

    fun activeControl() {
        _controlActive.value = true
    }

    fun deactivateControl() {
        _controlActive.value = false
    }

    fun startAuto() {
        _autoMode.value = true
    }

    fun stopAuto() {
        _autoMode.value = false
    }

    fun resetCycles() {
        _resetCycles.value = true
    }

    fun stopResetCycles() {
        _resetCycles.value = false
    }

    fun startStep() {
        _manualMode.value = true
    }

    fun stopStep() {
        _manualMode.value = false
    }
}