package com.example.inzynierka_app.viewmodel

import android.util.Log
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
class BlockViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _rightDir = MutableLiveData<Boolean>()
    val rightDir: LiveData<Boolean> = _rightDir

    private val _readData = MutableLiveData<ArrayList<ReadDataRequest>>()
    val readData: LiveData<ArrayList<ReadDataRequest>> = _readData

    fun readData(array: ArrayList<ReadDataRequest>) = viewModelScope.launch {
        val response = mainRepository.readArray(ReadArrayRequest(array))
            val responseBody = response.body()
            Log.i("BlockViewModel", responseBody?.araayRes.toString())
            if (response.isSuccessful) {
                if (responseBody?.araayRes != null) {
                    _readData.value = responseBody.araayRes
                    Log.i("BlockViewModel", responseBody.araayRes.toString())
                    //  readData(array)
                }
            }
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