package com.example.inzynierka_app.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.inzynierka_app.db.GripperError
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

    private var viewModelJob: Job? = null

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
                delay(100)
                gripper.startReadCycles(read_param)
                _cyclesNumber.value = gripper.cycles.value
                if (setCycles.value != "0") {
                    if (_cyclesNumber.value == setCycles.value) {
                        reachSetCycles()
                    }
                }
            }
        }
    }

    private fun reachSetCycles() {
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
        try{
            mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", write_param))
        } catch (exception: Exception){
            Log.e("TAG", exception.message ?: "NULL")
        }
    }


    fun writeDataParallel(step: String) = viewModelScope.launch {
        try {
            when (step) {
                "Step 1" -> {
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_2", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_3", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_4", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_5", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_6", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_7", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_8", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_1", true)))
                }
                "Step 2" -> {
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_1", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_3", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_4", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_5", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_6", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_7", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_8", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_2", true)))
                }
                "Step 3" -> {
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_1", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_2", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_4", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_5", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_6", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_7", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_8", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_3", true)))
                }
                "Step 4" -> {
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_1", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_2", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_3", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_5", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_6", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_7", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_8", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_4", true)))
                }
                "Step 5" -> {
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_1", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_2", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_3", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_4", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_6", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_7", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_8", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_5", true)))
                }
                "Step 6" -> {
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_1", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_2", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_3", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_4", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_5", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_7", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_8", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_6", true)))
                }
                "Step 7" -> {
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_1", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_2", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_3", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_4", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_5", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_6", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_8", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_7", true)))
                }
                "Step 8" -> {
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_1", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_2", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_3", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_4", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_5", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_6", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_7", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".app_KROK_8", true)))
                }
                else -> Log.i("AUTO", "Step not recognized")
            }
        } catch (exception: Exception){
            Log.e("TAG", exception.message ?: "NULL")
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

    fun pause() {
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

    fun insertRun(gripperError: GripperError) = viewModelScope.launch {
        mainRepository.insert(gripperError)
    }

    val errorsSortedByDate = mainRepository.getAllErrorsSortedByDate()

}