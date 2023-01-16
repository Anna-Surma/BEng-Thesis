package com.example.inzynierka_app.viewmodel

import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.inzynierka_app.*
import com.example.inzynierka_app.db.GripperError
import com.example.inzynierka_app.model.*
import com.example.inzynierka_app.repository.MainRepository
import com.example.inzynierka_app.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class GripperViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val CPUDataUseCases: CPUDataUseCaces
) : ViewModel() {

    private val _controlActive = MutableLiveData<Boolean>(false)
    val controlActive: LiveData<Boolean> = _controlActive

    private val _autoMode = MutableLiveData(false)
    val autoMode: LiveData<Boolean> = _autoMode

    private var timer = Timer()

    private var counter: CountDownTimer? = null

    private val _timerBaseTime = MutableLiveData<Long?>(null)
    val timerBaseTime: LiveData<Long?> = _timerBaseTime

    private val _isTimerRunning = MutableLiveData<Boolean>()
    val isTimerRunning: LiveData<Boolean> = _isTimerRunning

    private val _cyclesNumber = MutableLiveData("0")
    val cyclesNumber: LiveData<String> = _cyclesNumber

    private val _cyclesTime = MutableLiveData("0")
    val cyclesTime: LiveData<String> = _cyclesTime

    private val _manualMode = MutableLiveData(false)
    val manualMode: LiveData<Boolean> = _manualMode

    private var _isPause: Boolean = false

    val setCycles = MutableLiveData<String>()

    val setTime = MutableLiveData<String>()

    private val _errorResponse = MutableLiveData<ErrorType?>()
    val errorResponse: LiveData<ErrorType?> = _errorResponse

    private val _stepsResponse = MutableLiveData<Steps?>(Steps.STEP1) //to zmienić!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    val stepsResponse: LiveData<Steps?> = _stepsResponse

    private val _avrCyclesTime = MutableLiveData("0")
    val avrCyclesTime: LiveData<String> = _avrCyclesTime

    private val _durationCounter = MutableLiveData("0")
    val durationCounter: LiveData<String> = _durationCounter

    private val _CPUmode = MutableLiveData<String?>()
    val CPUmode: LiveData<String?> = _CPUmode

    var listOfSteps = mutableListOf<String>()

    var listStepsLD = mutableListOf<StepItem>()

    val setBlock = MutableLiveData<String>()

    var count = 0

    private val _blockSteps = MutableLiveData<MutableList<StepItem>>()
    val blockSteps: LiveData<MutableList<StepItem>> = _blockSteps


    var isBlockActive = false

    private var cycleViewModelJob: Job? = null

    private var viewModelErrorJob: Job? = null

    private var viewModelStepsJob: Job? = null

    private var viewModelCPUModeJob: Job? = null

    init {
        resetCycles()
        _cyclesNumber.value = "0"
    }

    fun onControlTbCheckedChanged(checked: Boolean) {
        if (checked) {
            readCPUMode()
            enableAppControl()
            readSteps(RequestArrays.STEPS.array)
            readErrors(RequestArrays.ERRORS.array)
            _isPause = false
            viewModelScope.launch {
                writeData(ParamsWrite("\"DB100\".mb_app_pause", false))
            }


        } else {
            stopReadCPUMode()
            _CPUmode.value = "none"
           disableAppControl()
            stopAuto()
            stopTimer()
            stopReadSteps()
            stopReadErrors()
            _isPause = false
            viewModelScope.launch {
                writeData(ParamsWrite("\"DB100\".mb_app_pause", false))
            }
            writeData2(ParamsWrite("\"DB100\".mb_app_step", false))
            listOfSteps.clear()
        }
    }

    fun enableAppControl() { //LEGIT
        viewModelScope.launch {
            writeData(ParamsWrite("\"DB100\".mb_app_control", true))
        }
        _controlActive.value = true
    }

    fun disableAppControl() { //LEGIT
        viewModelScope.launch {
            writeData(ParamsWrite("\"DB100\".mb_app_control", false))
        }
        _controlActive.value = false
    }

    fun readCPUMode() {
        viewModelCPUModeJob = viewModelScope.launch {
            while (isActive) {
                delay(30)
                val readModeResult = CPUDataUseCases.readCPUMode()
                when (readModeResult.status) {
                    Status.SUCCESS -> {_CPUmode.postValue(readModeResult.data) }
                    else -> Log.i("GripperViewModel", "Read cycle time ERROR")
                }
            }
        }
    }

    fun stopReadCPUMode() {
        viewModelScope.launch {
            viewModelCPUModeJob?.cancel()
        }
    }

    fun onStartBtnClicked() {
        if (controlActive.value == true && autoMode.value == false) {
                if (!_isPause) {
                   // resetCycles()
                    _cyclesNumber.value = "0"
                    setStartPoint(_stepsResponse.value!!.id)
                    timer.offset = 0 // LEGIT
                    counter = startCountDown(false, "") // PRAWIE LEGIT
                } else {
                    counter =
                        startCountDown(true, _durationCounter.value)
                }
                startAuto() // LEGIT
        }
    }

    private fun startAuto() {
        if (controlActive.value == true && autoMode.value == false) {
            viewModelScope.launch {
                writeData(ParamsWrite("\"DB100\".mb_app_auto", true))
                if(!_isPause){
                    startReadCyclesAndTime()
                }
                _autoMode.value = true
                _isPause = false
                writeData(ParamsWrite("\"DB100\".mb_app_pause", false))
                counter?.start()
                startTimer() // LEGIT
            }
            viewModelScope.launch {
                writeData(ParamsWrite("\"DB100\".mb_delete_cycles", false))
            }
        }
    }

    fun startTimer() {
        _timerBaseTime.value = timer.setBaseTime()
        _isTimerRunning.value = true
    }

    fun onStopBtnClicked() {
        if (autoMode.value == true) {
            stopAuto()
        }
    }

    fun stopAuto() {
        viewModelScope.launch {
            writeData(ParamsWrite("\"DB100\".mb_app_auto", false))
            writeData(ParamsWrite("\"DB100\".mb_app_pause", false))
        }
        stopTimer()
        stopReadCyclesNrAndTime()
        counter?.cancel()
        _autoMode.value = false
        _isPause = false
        resetCycles()
    }

    private fun stopTimer() {
        _isTimerRunning.value = false
    }

    fun onPauseBtnClicked() {
        if (autoMode.value == true && _isPause == false) {
            pauseAuto()
            pauseTimer()
        }
    }

    fun pauseAuto() {
        _isPause = true
        viewModelScope.launch {
            writeData(ParamsWrite("\"DB100\".mb_app_auto", false))
            writeData(ParamsWrite("\"DB100\".mb_app_pause", true))
        }
       // stopReadCyclesNrAndTime()
        counter?.cancel()
        _autoMode.value = false
    }

    fun pauseTimer() {
        timer.offset = timer.getElapsedRealtime() - timerBaseTime.value!!.toLong()
        _isTimerRunning.value = false
    }

    fun startReadCyclesAndTime() {
        var sum = 0
        var cycleNrTemp = 0
        cycleViewModelJob = viewModelScope.launch {
            while (isActive) {
                delay(100)
                val readCycleResult = CPUDataUseCases.readCPUValue(ParamsRead("\"DB100\".mw_cycles"))
                if(_cyclesNumber.value != readCycleResult.data?.dropLast(2)){
                    when(readCycleResult.status){
                        Status.SUCCESS -> _cyclesNumber.postValue(readCycleResult.data?.dropLast(2))
                        else -> Log.i("GripperViewModel", "Read cycle ERROR")
                    }
                }

                val readCycleTimeResult = CPUDataUseCases.readCPUValue(ParamsRead("\"DB100\".mw_cycle_time"))
                if(_cyclesNumber.value!!.toInt() != cycleNrTemp) {
                    when (readCycleTimeResult.status) {
                        Status.SUCCESS -> {
                            if (readCycleTimeResult.data == "null") {
                                _cyclesTime.postValue("0")
                            }
                            else{
                                _cyclesTime.postValue(readCycleTimeResult.data?.dropLast(2))
                                sum = countAvrTime(
                                    sum,
                                    _cyclesTime.value!!.toInt(),
                                    _cyclesNumber.value!!.toInt()
                                )
                            }
                            cycleNrTemp = _cyclesNumber.value!!.toInt()
                        }
                        else -> Log.i("GripperViewModel", "Read cycle time ERROR")
                    }
                }
                if (setCycles.value != "0") {
                    if (_cyclesNumber.value == setCycles.value) {
                        reachSetValue()
                    }
                }
                if (_durationCounter.value == "0" && (setTime.value != null && setTime.value != "" && setTime.value != "0")) {
                    reachSetValue()
                }
            }
        }
    }

    fun countAvrTime(sum: Int, cycleTime: Int, nrOfCycle: Int): Int{
        try {
            _avrCyclesTime.value = ((sum + cycleTime) / nrOfCycle).toString()
        } catch (e : Exception){
            Log.i("GripperViewModel", "Divided by zero")
        }
        return (sum + cycleTime)
    }

    fun stopReadCyclesNrAndTime() {
        cycleViewModelJob?.cancel()
    }

    fun reachSetValue() {
        stopAuto()
        _manualMode.value = false
        stopReadCyclesNrAndTime()
        stopTimer()
    }

    fun resetCycles() {
        viewModelScope.launch {
            writeData(ParamsWrite("\"DB100\".mb_delete_cycles", true))
        }
    }

    fun startCountDown(isPause: Boolean, remainingTime: String?): CountDownTimer? {
        if (setTime.value != null && setTime.value != "" && setTime.value != "0") {
            val setDurationLong = setTime.value?.toLong()?.plus(1)

            if (setDurationLong != null) {
                val durationMs = setDurationLong * 1000L
                if (isPause) {
                    val durationPauseMs = remainingTime!!.toLong() * 1000L
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
        return null
    }

    fun readSteps(read_array_item: ArrayList<ReadDataRequest>) {
        viewModelStepsJob = viewModelScope.launch {
            while (isActive) {
                delay(30)
                val readStepResult = CPUDataUseCases.readCPUStep(read_array_item)
                when (readStepResult.status) {
                    Status.SUCCESS -> {_stepsResponse.postValue(readStepResult.data) }
                    else -> Log.i("GripperViewModel", "Read cycle time ERROR")
                }
            }
        }
    }

    fun stopReadSteps() {
        viewModelStepsJob?.cancel()
    }

    fun setStartPoint(startPoint: Int) {
        viewModelScope.launch{
            writeData(ParamsWrite("\"DB100\".mb_app_step_set", startPoint))
        }
    }

    fun readErrors(read_array_item: ArrayList<ReadDataRequest>) {
        viewModelErrorJob = viewModelScope.launch {
            while (isActive) {
                delay(30)
                val readErrorResult = CPUDataUseCases.readCPUError(read_array_item)
                when (readErrorResult.status) {
                    Status.SUCCESS -> {
                            _errorResponse.postValue(readErrorResult.data)
                    }
                    else -> Log.i("COS", "ERROR")
                }
            }
        }
    }

    fun stopReadErrors() {
        viewModelErrorJob?.cancel()
    }

    fun insertError(gripperError: GripperError) = viewModelScope.launch {
        mainRepository.insert(gripperError)
    }

    val errorsSortedByDate = mainRepository.getAllErrorsSortedByDate()

    fun deleteErrors() {
        viewModelScope.launch {
            mainRepository.delete()
        }
    }

    suspend fun writeData(write_param: ParamsWrite) {
                val writaValueResult = CPUDataUseCases.writeCPUValue(write_param)
    }

    fun writeData2(write_param: ParamsWrite) {
        viewModelScope.launch {
            val writaValueResult = CPUDataUseCases.writeCPUValue(write_param)
        }
    }
    fun quitErrors(){
        viewModelScope.launch {
            writeData(ParamsWrite("\"DB100\".mb_app_btn_error", true))
            writeData(ParamsWrite("\"DB100\".mb_app_btn_error", false))
            readErrors(RequestArrays.ERRORS.array)
        }
    }

    fun writeStartPoint(step: String) {
        if(_controlActive.value == true){
            viewModelScope.launch {
                CPUDataUseCases.writeCPUStartPoint(step)
            }
        }
    }

    fun writeCPUMode(mode: String) {
        if(_controlActive.value == true) {
            viewModelScope.launch {
                CPUDataUseCases.writeCPUMode(mode)
            }
        }
    }

    fun startBlock() {
        if (_controlActive.value == true) {
            if(!listOfSteps.isNullOrEmpty()){
                isBlockActive = true
                val temp = setBlock.value
                viewModelScope.launch {
                    writeData2(ParamsWrite("\"DB100\".mb_app_step", true))
                    if(setBlock.value != "0" && setBlock.value != "" && setBlock.value != " " && setBlock.value != null){
                        repeat(temp!!.toInt()){
                            for (step in listOfSteps) {
                                CPUDataUseCases.writeCPUStartPoint(step)
                                delay(500)
                            }
                        }
                    }
                    else {
                        while (isBlockActive){
                            for (step in listOfSteps) {
                                CPUDataUseCases.writeCPUStartPoint(step)
                                delay(500)
                            }
                        }
                    }
                    writeData2(ParamsWrite("\"DB100\".mb_app_step", false))
                    listOfSteps.clear()
                }
            }
        }
    }

    fun chooseStep(step: String) {
        listOfSteps.add(step)
        listStepsLD.add(StepItem(step))
        _blockSteps.value = listStepsLD

        Log.i("DUPA", _blockSteps.value.toString())
    }

    fun stopBlock(){
        isBlockActive = false
    }

    fun startStep() {
        _manualMode.value = true
    }

    fun stopStep() {
        _manualMode.value = false
    }
}