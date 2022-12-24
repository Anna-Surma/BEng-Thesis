package com.example.inzynierka_app.use_case

import com.example.inzynierka_app.Resource
import com.example.inzynierka_app.model.ParamsWrite
import com.example.inzynierka_app.model.WriteDataRequest
import com.example.inzynierka_app.repository.MainRepository
import javax.inject.Inject

class WriteCPUValueUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(write_param: ParamsWrite)
            : Resource<Any?> {
        mainRepository.writeData(WriteDataRequest(1, "2.0", "PlcProgram.Write", write_param)).let {
            if (it.isSuccessful) {
                return Resource.success(it.body()?.result)
            } else {
                return Resource.error("Data not read", null)
            }
        }
    }
}