package com.example.inzynierka_app.use_case

import com.example.inzynierka_app.other.Resource
import com.example.inzynierka_app.model.CPUModeRequest
import com.example.inzynierka_app.repository.MainRepository
import javax.inject.Inject

class ReadCPUModeUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke()
            : Resource<String?> {
        mainRepository.readCPUMode(CPUModeRequest(1, "2.0", "Plc.ReadOperatingMode")).let {
            if (it.isSuccessful) {
                return Resource.success(it.body()?.result.toString())
            } else {
                return Resource.error("Data not read", null)
            }
        }
    }
}