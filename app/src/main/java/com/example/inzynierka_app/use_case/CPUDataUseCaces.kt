package com.example.inzynierka_app.use_case
import javax.inject.Inject

data class CPUDataUseCaces
@Inject constructor(
    val readCPUError: ReadCPUErrorUseCase,
    val readCPUMode: ReadCPUModeUseCase,
    val readCPUStep: ReadCPUStepUseCase,
    val readCPUValue: ReadCPUValueUseCase,
    val writeCPUStartPoint: WriteCPUStartPointUseCase,
    val writeCPUValue: WriteCPUValueUseCase,
    val writeCPUMode: WriteCPUModeUseCase
)