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
class AutoViewModel @Inject constructor(
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

    init{
        _controlActive.value = false
        _autoMode.value = false
    }

    //What with onFailure???
    fun readData(read_param: Params) = viewModelScope.launch {
        while(true){
            delay(100)
            val response = mainRepository.readData(ReadDataRequest(1, "2.0", "PlcProgram.Read", read_param))
            val responseBody = response.body()
            if(response.isSuccessful){
                if (responseBody?.result != null) {
                    _readData.value = responseBody.result.toString()
                    Log.i("AutoViewModel", "READ" + responseBody.result.toString())
                }
            }
        }
    }

     fun write_data(write_param: ParamsWriteVar) = viewModelScope.launch {
         val response = mainRepository.write_data(WriteDataRequest(1, "2.0", "PlcProgram.Write", write_param))
                     val responseBody = response.body()
                     if (response.isSuccessful) {
                         Log.i("AutoViewModel", "RESULT= "+responseBody?.result.toString())
                         if (responseBody?.result != null) {
                             _writeData.value = responseBody.result
                         }
                     }
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

    fun stopResetCycles(){
        _resetCycles.value = false
    }
}