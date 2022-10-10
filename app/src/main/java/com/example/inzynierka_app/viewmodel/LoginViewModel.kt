package com.example.inzynierka_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inzynierka_app.model.*
import com.example.inzynierka_app.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private var params: LoginParams = LoginParams("json", "json")

    private val _logInEvent = MutableLiveData(LogInEvent(false, null))
    val logInEvent: LiveData<LogInEvent>
        get() = _logInEvent

    fun onSignInButtonClicked() {
        loginUser(params)
    }
        private fun loginUser(param: LoginParams) = viewModelScope.launch {
            mainRepository.login(LoginRequest(id = 0, jsonrpc = "2.0", method = "Api.Login", param))
                .enqueue(object : Callback<LoginResponse> {

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.i("LoginFragment", t.message.toString())
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val loginResponse = response.body()
                    if (response.isSuccessful) {
                        if (loginResponse?.result?.token != null) {
                            mainRepository.saveAuthToken(loginResponse.result.token)
                            _logInEvent.value = LogInEvent(true, loginResponse.result.token)
                        }
                        else{
                            _logInEvent.value = LogInEvent(true, mainRepository.fetchAuthToken())
                        }
                    }
                }
            })
    }
}