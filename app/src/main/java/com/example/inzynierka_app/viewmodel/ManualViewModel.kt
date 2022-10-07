package com.example.inzynierka_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inzynierka_app.model.*
import com.example.inzynierka_app.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManualViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _manualMode = MutableLiveData<Boolean>()
    val manualMode: LiveData<Boolean> = _manualMode

    private val _writeData = MutableLiveData<Boolean>()
    val writeData: LiveData<Boolean> = _writeData

    init {
        _manualMode.value = false
    }

    fun write_data(write_param: ParamsWriteVar) = viewModelScope.launch {
        val response = mainRepository.write_data(WriteDataRequest(1, "2.0", "PlcProgram.Write", write_param))
        val responseBody = response.body()
        if (response.isSuccessful) {
            if (responseBody?.result != null) {
                _writeData.value = responseBody.result
            }
        }
    }

    fun startStep(){
        _manualMode.value = true
    }

    fun stopStep(){
        _manualMode.value = false
    }
}