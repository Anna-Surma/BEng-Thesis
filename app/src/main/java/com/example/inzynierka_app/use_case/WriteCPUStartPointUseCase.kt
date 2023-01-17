package com.example.inzynierka_app.use_case

import android.util.Log
import com.example.inzynierka_app.other.Steps
import com.example.inzynierka_app.repository.MainRepository
import javax.inject.Inject

class WriteCPUStartPointUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(step: String) {
        when (step) {
            "Step 1" -> {
                mainRepository.writeArray(Steps.STEP1.request)
            }
            "Step 2" -> {
                mainRepository.writeArray(Steps.STEP2.request)
            }
            "Step 3" -> {
                mainRepository.writeArray(Steps.STEP3.request)
            }
            "Step 4" -> {
                mainRepository.writeArray(Steps.STEP4.request)
            }
            "Step 5" -> {
                mainRepository.writeArray(Steps.STEP5.request)
            }
            "Step 6" -> {
                mainRepository.writeArray(Steps.STEP6.request)
            }
            "Step 7" -> {
                mainRepository.writeArray(Steps.STEP7.request)
            }
            "Step 8" -> {
                mainRepository.writeArray(Steps.STEP8.request)
            }
            else -> Log.i("AUTO", "Step not recognized")
        }
    }
}

