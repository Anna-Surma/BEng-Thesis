package com.example.inzynierka_app.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.inzynierka_app.model.*
import com.example.inzynierka_app.repository.GripperDataPullWorker
import com.example.inzynierka_app.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GripperViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val gripper: GripperDataPullWorker,
    //  loginUseCase: LoginUseCase //Clean Architecture // Aplication, domain, data(repo)
) : ViewModel() {

    private val _readData = MutableLiveData<String>()
    val readData: LiveData<String> = _readData

    private val _writeData = MutableLiveData<Boolean>()
    val writeData: LiveData<Boolean> = _writeData

    private val _controlActive = MutableLiveData<Boolean>()
    val controlActive: LiveData<Boolean> = _controlActive

    private val _autoMode = MutableLiveData<Boolean>()
    val autoMode: LiveData<Boolean> = _autoMode

    private val _cyclesNumber = MutableLiveData("0")
    val cyclesNumber: LiveData<String> = _cyclesNumber

    private val _manualMode = MutableLiveData<Boolean>()
    val manualMode: LiveData<Boolean> = _manualMode

    private val _readDataArray = MutableLiveData<ArrayList<ReadDataRequest>>()
    val readDataArray: LiveData<ArrayList<ReadDataRequest>> = _readDataArray

    private val _isRunning = MutableLiveData<Boolean>()
    val isRunning: LiveData<Boolean> = _isRunning

    private val _isPause = MutableLiveData<Boolean>()
    val isPause: LiveData<Boolean> = _isPause

    private val _reachSetCycles = MutableLiveData<Boolean>()
    val reachSetCycles: LiveData<Boolean> = _reachSetCycles

    val setCycles = MutableLiveData<String>()
      get() = field

    var viewModelJob: Job? = null

    init {
        _controlActive.value = false
        _autoMode.value = false
        _manualMode.value = false
        _isRunning.value = false
        _isPause.value = false
        _cyclesNumber.value = gripper.cycles.value
        resetCycles()
    }

    fun stopReadCycles() {
        gripper.stopReadCycles()
        viewModelJob?.cancel()
    }

    fun startReadCycles(read_param: Params) {
        viewModelJob = viewModelScope.launch {
            gripper.synchronizing = true
            while (gripper.synchronizing) {
                delay(1000)
                gripper.startReadCycles(read_param)
                _cyclesNumber.value = gripper.cycles.value
                if(setCycles.value != "0"){
                    if(_cyclesNumber.value == setCycles.value){
                        reachSetCycles()
                    }
                }

            }
        }
    }

    fun reachSetCycles(){
        _reachSetCycles.value = true
        stopReadCycles()
        _isRunning.value = false
        _autoMode.value = false
        _manualMode.value = false
    }


    fun writeData(write_param: ParamsWriteVar) = viewModelScope.launch {
        // _writeData.value = mainRepository.sendCycles(123)
        // CyclesResponse(value?, failure?)
        // Maybe<Boolean>

        val response =
            mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", write_param))
        val responseBody = response.body()
        if (response.isSuccessful) {
            if (responseBody?.result != null) {
                _writeData.value = responseBody.result
            }
        }
    }

    fun activeControl() {
        _controlActive.value = true
        _isPause.value = false
    }

    fun deactivateControl() {
        _controlActive.value = false
        _autoMode.value = false
        _isPause.value = false
        _isRunning.value = false
    }

    fun startAuto() {
        _autoMode.value = true
        _isRunning.value = true
        _isPause.value = false
        _reachSetCycles.value = false
    }

    fun stopAuto() {
        _autoMode.value = false
        _isRunning.value = false
        _isPause.value = false
    }

    fun resetCycles() {
        _cyclesNumber.value = "0"
        gripper.cycles.value = "0"
        writeData(ParamsWriteVar("\"Data\".app_reset_liczba_cykli", true))
        writeData(ParamsWriteVar("\"Data\".app_reset_liczba_cykli", false))
    }

    fun pause(){
        _autoMode.value = false
        _isRunning.value = false
        _isPause.value = true
    }

    fun startStep() {
        _manualMode.value = true
    }

    fun stopStep() {
        _manualMode.value = false
    }
}