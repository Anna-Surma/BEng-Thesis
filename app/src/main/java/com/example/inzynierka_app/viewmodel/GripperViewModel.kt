package com.example.inzynierka_app.viewmodel

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.*
import com.example.inzynierka_app.ReadArrayRequestItem
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

    private val _cyclesTime = MutableLiveData("0")
    val cyclesTime: LiveData<String> = _cyclesTime

    private val _manualMode = MutableLiveData<Boolean>()
    val manualMode: LiveData<Boolean> = _manualMode

    private val _isRunning = MutableLiveData<Boolean>()
    val isRunning: LiveData<Boolean> = _isRunning

    private val _isPause = MutableLiveData<Boolean>()
    val isPause: LiveData<Boolean> = _isPause

    private val _reachSetCycles = MutableLiveData<Boolean>()
    val reachSetCycles: LiveData<Boolean> = _reachSetCycles

    val setCycles = MutableLiveData<String>()

    private val _arrayErrorResponse = MutableLiveData<ArrayList<ArrayResponseItem>>()
    val arrayErrorResponse: LiveData<ArrayList<ArrayResponseItem>> = _arrayErrorResponse

    private val _avrCyclesTime = MutableLiveData<String>()
    val avrCyclesTime: LiveData<String> = _avrCyclesTime

    private val _stepsArrayResponse = MutableLiveData<ArrayList<ArrayResponseItem>>()
    val stepsArrayResponse: LiveData<ArrayList<ArrayResponseItem>> = _stepsArrayResponse

    private val _isCycleSet = MutableLiveData<Boolean>()
    val isCycleSet: LiveData<Boolean> = _isCycleSet

    private val _isTimeSet = MutableLiveData<Boolean>()
    val isTimeSet: LiveData<Boolean> = _isTimeSet

    private val _durationCounter = MutableLiveData<String>()
    val durationCounter: LiveData<String> = _durationCounter

    private val _CPUmode = MutableLiveData<String>()
    val CPUmode: LiveData<String> = _CPUmode

    val setTime = MutableLiveData<String>()

    private var viewModelJob: Job? = null

    private var viewModelErrorJob: Job? = null

    private var viewModelStepsJob: Job? = null

    private var viewModelCPUModeJob: Job? = null

    init {
        _controlActive.value = false
        _autoMode.value = false
        _manualMode.value = false
        _isRunning.value = false
        _isPause.value = false
        _cyclesNumber.value = gripper.cycles.value
        resetCycles()
        _isCycleSet.value = false
        _isTimeSet.value = false
    }

    fun stopReadCycles() {
        gripper.stopReadCycles()
        viewModelJob?.cancel()
    }

    fun startReadCycles(read_param: ParamsRead) {
        var sum = 0
        var cycle_change_hold: String? = "0"
        val cycles = arrayListOf<Int>()
        var averageTime: Int
        viewModelJob = viewModelScope.launch {
            gripper.synchronizing = true
            while (gripper.synchronizing) {
                delay(100)
                gripper.startReadCycles(read_param)
                gripper.startReadCyclesTime(ParamsRead("\"Data\".mw_cycle_time"))
                _cyclesNumber.value = gripper.cycles.value
                _cyclesTime.value = gripper.cyclesTime.value
                if(sum==0 || (cycle_change_hold != _cyclesNumber.value)){
                    cycles.add(_cyclesTime.value!!.toInt())
                    sum = sum+_cyclesTime.value!!.toInt()
                    averageTime = sum/cycles.size
                    _avrCyclesTime.value = averageTime.toString()
                    cycle_change_hold = _cyclesNumber.value
                }
                if (setCycles.value != "0") {
                    if (_cyclesNumber.value == setCycles.value) {
                        reachSetCycles()
                    }
                }
                if (_durationCounter.value == "0" && (setCycles.value == "0" || setCycles.value == null || setCycles.value == "")) {
                    reachSetDuration()
                }
            }
        }
    }

    fun cycleOrTimeCheck(){
        when {
            (setTime.value != "" && setTime.value != null && setTime.value != "0") -> {_isTimeSet.value = true
                _isCycleSet.value = false }
            (setCycles.value != "" && setCycles.value != null && setCycles.value != "0") -> {_isCycleSet.value = true
                _isTimeSet.value = false }
            else -> {_isCycleSet.value = false
                _isTimeSet.value = false}
        }
    }

    fun readErrors(read_array_item: ArrayList<ReadArrayRequestItem>) {
        viewModelErrorJob = viewModelScope.launch {
            while (true) {
                delay(30)
                gripper.readErrors(read_array_item)
                _arrayErrorResponse.value = gripper.arrayErrorResponseLiveData.value
            }
        }
    }

    fun readSteps(read_array_item: ArrayList<ReadArrayRequestItem>) {
        viewModelStepsJob = viewModelScope.launch {
            while (true) {
                gripper.readSteps(read_array_item)
                _stepsArrayResponse.value = gripper.stepsArrayResponseLiveData.value
            }
        }
    }

    fun readCPUMode() {
        viewModelCPUModeJob = viewModelScope.launch {
            while (true) {
                delay(30)
                gripper.readCPUMode()
                _CPUmode.value = gripper.CPUmode.value
            }
        }
    }

    fun stopReadErrors() {
        viewModelErrorJob?.cancel()
    }

    fun stopReadCPUMode() {
        viewModelCPUModeJob?.cancel()
        _CPUmode.value = "none"

    }

//    fun stopReadSteps() {
//        viewModelStepsJob?.cancel()
//    }

    private fun reachSetCycles() {
        _reachSetCycles.value = true
        stopReadCycles()
        _isRunning.value = false
        _autoMode.value = false
        _manualMode.value = false
    }

    private fun reachSetDuration() {
        _reachSetCycles.value = true
        _isRunning.value = false
        _autoMode.value = false
        _manualMode.value = false
    }

    fun writeData(write_param: ParamsWrite) = viewModelScope.launch {
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
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_2", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_3", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_4", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_5", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_6", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_7", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_8", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_1", true)))
                }
                "Step 2" -> {
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_1", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_3", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_4", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_5", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_6", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_7", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_8", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_2", true)))
                }
                "Step 3" -> {
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_1", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_2", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_4", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_5", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_6", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_7", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_8", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_3", true)))
                }
                "Step 4" -> {
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_1", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_2", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_3", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_5", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_6", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_7", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_8", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_4", true)))
                }
                "Step 5" -> {
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_1", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_2", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_3", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_4", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_6", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_7", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_8", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_5", true)))
                }
                "Step 6" -> {
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_1", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_2", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_3", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_4", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_5", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_7", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_8", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_6", true)))
                }
                "Step 7" -> {
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_1", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_2", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_3", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_4", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_5", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_6", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_8", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_7", true)))
                }
                "Step 8" -> {
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_1", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_2", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_3", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_4", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_5", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_6", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_7", false)))
                    mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", ParamsWrite("\"Data\".mb_step_8", true)))
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
        writeData(ParamsWrite("\"Data\".mb_delete_cycles", true))
        writeData(ParamsWrite("\"Data\".mb_delete_cycles", false))
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

    fun startCountDown(isPause: Boolean, remainingTime: String): CountDownTimer?{
        if(setTime.value != null && setTime.value != "" && setTime.value == "0") {
            if (setCycles.value == null || setCycles.value == "" || setCycles.value == "0") {
                if (setTime.value != null) {
                    val setDurationLong = setTime.value?.toLong()?.plus(1)
                    if ((setDurationLong != null && setDurationLong != 0L)) {
                        val durationMs = setDurationLong * 1000L
                        if (isPause) {
                            val durationPauseMs = remainingTime.toLong() * 1000L
                            return (object : CountDownTimer(durationPauseMs, 1000) {
                                override fun onTick(millisUntilFinished: Long) {
                                    _durationCounter.value = (millisUntilFinished / 1000).toString()
                                }

                                override fun onFinish() {
                                }
                            })
                        } else {
                            return (object : CountDownTimer(durationMs, 1000) {
                                override fun onTick(millisUntilFinished: Long) {
                                    _durationCounter.value = (millisUntilFinished / 1000).toString()
                                }

                                override fun onFinish() {
                                }
                            })
                        }
                    }
                }
            }
        }
        return null
    }
}