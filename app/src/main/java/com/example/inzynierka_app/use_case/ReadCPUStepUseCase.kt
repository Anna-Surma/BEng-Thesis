package com.example.inzynierka_app.use_case

import com.example.inzynierka_app.other.Resource
import com.example.inzynierka_app.other.Steps
import com.example.inzynierka_app.model.ReadDataRequest
import com.example.inzynierka_app.repository.MainRepository
import javax.inject.Inject

class ReadCPUStepUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(read_array_item: ArrayList<ReadDataRequest>)
            : Resource<Steps> {
        mainRepository.readArray(read_array_item).let {
            if (it.isSuccessful) {
                val response = it.body()
                if (response != null) {
                    for (nr in response) {
                        if (nr.result) {
                            when (nr.id) {
                                1 -> {
                                    return Resource.success(Steps.STEP1)
                                }
                                2 -> {
                                    return Resource.success(Steps.STEP2)
                                }
                                3 -> {
                                    return Resource.success(Steps.STEP3)
                                }
                                4 -> {
                                    return Resource.success(Steps.STEP4)
                                }
                                5 -> {
                                    return Resource.success(Steps.STEP5)
                                }
                                6 -> {
                                    return Resource.success(Steps.STEP6)
                                }
                                7 -> {
                                    return Resource.success(Steps.STEP7)
                                }
                                8 -> {
                                    return Resource.success(Steps.STEP8)
                                }
                                else -> {
                                    return Resource.success(Steps.STEP1)
                                }
                            }
                        }
                    }
                }
            } else {
                return Resource.error("Data not read", null)
            }
        }
        return Resource.error("Data not read", null)
    }
}