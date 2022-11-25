package com.example.inzynierka_app.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.inzynierka_app.ArrayRequestItem
import com.example.inzynierka_app.ArrayResponseItem
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

    private val _controlActive = MutableLiveData<Boolean>()
    val controlActive: LiveData<Boolean> = _controlActive

    private val _autoMode = MutableLiveData<Boolean>()
    val autoMode: LiveData<Boolean> = _autoMode

    private val _cyclesNumber = MutableLiveData("0")
    val cyclesNumber: LiveData<String> = _cyclesNumber

    private val _manualMode = MutableLiveData<Boolean>()
    val manualMode: LiveData<Boolean> = _manualMode

    private val _isRunning = MutableLiveData<Boolean>()
    val isRunning: LiveData<Boolean> = _isRunning

    private val _isPause = MutableLiveData<Boolean>()
    val isPause: LiveData<Boolean> = _isPause

    private val _reachSetCycles = MutableLiveData<Boolean>()
    val reachSetCycles: LiveData<Boolean> = _reachSetCycles

    val setCycles = MutableLiveData<String>()

    private val _arrayResponse = MutableLiveData<ArrayList<ArrayResponseItem>>()
    val arrayResponse: LiveData<ArrayList<ArrayResponseItem>> = _arrayResponse

    private var viewModelJob: Job? = null

    private var viewModelErrorJob: Job? = null

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

    fun readErrors(read_array_item: ArrayList<ArrayRequestItem>) {
        viewModelErrorJob = viewModelScope.launch {
            while (true) {
                delay(100)
                gripper.readErrors(read_array_item)
                _arrayResponse.value = gripper.arrayResponseLiveData.value
            }
        }
    }

    fun stopReadErrors() {
        viewModelErrorJob?.cancel()
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
        try {
            mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", write_param))
        } catch (exception: Exception) {
            Log.e("TAG", exception.message ?: "NULL")
        }
    }

    fun writeDataParallel(step: String) = viewModelScope.launch {
        try {
            when (step) {
                "Step 1" -> {
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_2", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_3", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_4", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_5", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_6", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_7", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_8", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_1", true)))
                }
                "Step 2" -> {
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_1", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_3", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_4", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_5", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_6", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_7", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_8", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_2", true)))
                }
                "Step 3" -> {
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_1", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_2", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_4", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_5", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_6", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_7", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_8", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_3", true)))
                }
                "Step 4" -> {
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_1", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_2", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_3", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_5", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_6", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_7", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_8", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_4", true)))
                }
                "Step 5" -> {
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_1", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_2", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_3", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_4", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_6", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_7", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_8", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_5", true)))
                }
                "Step 6" -> {
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_1", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_2", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_3", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_4", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_5", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_7", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_8", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_6", true)))
                }
                "Step 7" -> {
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_1", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_2", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_3", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_4", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_5", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_6", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_8", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_7", true)))
                }
                "Step 8" -> {
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_1", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_2", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_3", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_4", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_5", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_6", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_7", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWriteVar("\"Data\".mb_step_8", true)))
                }
                else -> Log.i("AUTO", "Step not recognized")
            }
        } catch (exception: Exception) {
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
        writeData(ParamsWriteVar("\"Data\".mb_delete_cycles", true))
        writeData(ParamsWriteVar("\"Data\".mb_delete_cycles", false))
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

    fun deleteErrors() {
        viewModelScope.launch {
            mainRepository.delete()
        }
    }
}