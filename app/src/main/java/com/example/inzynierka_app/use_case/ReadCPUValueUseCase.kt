package com.example.inzynierka_app.use_case

import com.example.inzynierka_app.Resource
import com.example.inzynierka_app.model.ParamsRead
import com.example.inzynierka_app.model.ReadDataRequest
import com.example.inzynierka_app.repository.MainRepository
import javax.inject.Inject

class ReadCPUValueUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(read_param: ParamsRead)
            : Resource<String?> {
        mainRepository.readData(ReadDataRequest(1, "2.0", "PlcProgram.Read", read_param)).let {
            if (it.isSuccessful) {
                return Resource.success(it.body()?.result.toString())
            } else {
                return Resource.error("Data not read", null)
            }
        }
    }
}