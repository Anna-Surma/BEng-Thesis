package com.example.inzynierka_app.use_case

import android.util.Log
import com.example.inzynierka_app.Resource
import com.example.inzynierka_app.model.CPUModeRequest
import com.example.inzynierka_app.repository.MainRepository
import javax.inject.Inject

class ReadCPUModeUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke()
            : Resource<String?> {
        Log.i("ViewModel", "ReadCPUModeUseCase START")
        mainRepository.readCPUMode(CPUModeRequest(1, "2.0", "Plc.ReadOperatingMode")).let {
            if (it.isSuccessful) {
                Log.i("ViewModel", "ReadCPUModeUseCase DIRING")
                return Resource.success(it.body()?.result.toString())
            } else {
                return Resource.error("Data not read", null)
            }
        }
    }
}