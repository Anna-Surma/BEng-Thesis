package com.example.inzynierka_app.use_case

import com.example.inzynierka_app.model.ParamsCPUWrite
import com.example.inzynierka_app.model.WriteCPUModeRequest
import com.example.inzynierka_app.repository.MainRepository
import javax.inject.Inject

class WriteCPUModeUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(mode: String) {
        mainRepository.writeCPUMode(
            WriteCPUModeRequest(
                1,
                "2.0",
                "Plc.RequestChangeOperatingMode",
                ParamsCPUWrite(mode.lowercase())
            )
        )
    }
}