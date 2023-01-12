package com.example.inzynierka_app.use_case

import com.example.inzynierka_app.ErrorType
import com.example.inzynierka_app.Resource
import com.example.inzynierka_app.model.ReadDataRequest
import com.example.inzynierka_app.repository.MainRepository
import javax.inject.Inject

class ReadCPUErrorUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(read_array_item: ArrayList<ReadDataRequest>)
            : Resource<ErrorType> {
        mainRepository.readArray(read_array_item).let {
            if (it.isSuccessful) {
                val response = it.body()
                if (response != null) {
                    for (nr in response) {
                        if (nr.result) {
                            when (nr.id) {
                                1 -> {
                                    return Resource.success(ErrorType.HOR_LEFT)
                                }
                                2 -> {
                                    return Resource.success(ErrorType.HOR_RIGHT)
                                }
                                3 -> {
                                    return Resource.success(ErrorType.VTK_TOP)
                                }
                                4 -> {
                                    return Resource.success(ErrorType.VTK_DOWN)
                                }
                                5 -> {
                                    return Resource.success(ErrorType.GRIPPER)
                                }
                                6 -> {
                                    return Resource.success(ErrorType.PUT)
                                }
                                7 -> {
                                    return Resource.success(ErrorType.NOT_HALT)
                                }
                                else -> {
                                    return Resource.success(ErrorType.NETWORK)
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