package com.example.inzynierka_app.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inzynierka_app.other.ErrorType
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

    private var params: ParamsLogin = ParamsLogin("json", "json")

    private val _logInEvent = MutableLiveData(LogInEvent(false, null))
    val logInEvent: LiveData<LogInEvent>
        get() = _logInEvent

    private val _loginErrorMessage = MutableLiveData<Int?>(null)
    val loginErrorMessage: LiveData<Int?> = _loginErrorMessage

    private val _networkErrorMessageBox = MutableLiveData<Int?>(null)
    val networkErrorMessageBox: LiveData<Int?> = _networkErrorMessageBox

    fun onSignInButtonClicked() {
        loginUser(params)
    }

    private fun loginUser(param: ParamsLogin) = viewModelScope.launch {
        mainRepository.login(LoginRequest(id = 0, jsonrpc = "2.0", method = "Api.Login", param))
            .enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    assignNetworkError(ErrorType.NETWORK.errorDesc)
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
                        } else {
                            _logInEvent.value = LogInEvent(true, mainRepository.fetchAuthToken())
                            assignFullError(ErrorType.LOGIN.errorDesc)
                        }
                    }
                }
            })
    }

    private fun assignFullError(errorMessage: Int?) {
        _loginErrorMessage.value = errorMessage
    }

    private fun assignNetworkError(errorMessage: Int?) {
        _networkErrorMessageBox.value = errorMessage
    }
}