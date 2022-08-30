package com.example.inzynierka_app.viewmodel

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.inzynierka_app.api.ApiClient
import com.example.inzynierka_app.api.SessionManager
import com.example.inzynierka_app.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private var params: LoginParams
    private lateinit var sessionManager: SessionManager

    private val _logInEvent = MutableLiveData(LogInEvent(false, null))
    val logInEvent: LiveData<LogInEvent>
        get() = _logInEvent

    init {
        params = LoginParams("json", "json")
    }

    fun onSignInButtonClicked() {
        loginUser(params)
    }

    private fun loginUser(param: LoginParams) {
        ApiClient().getService()?.login(LoginRequest(id = 0, jsonrpc = "2.0", method = "Api.Login", param))
            ?.enqueue(object : Callback<LoginResponse> {

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.i("LoginActivity", t.message.toString())
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val loginResponse = response.body()
                    if (response.isSuccessful) {

                        if (loginResponse?.result?.token != null) {
                            sessionManager.saveAuthToken(loginResponse.result.token)
                            _logInEvent.value = LogInEvent(true, loginResponse.result.token)
                        }
                        else{
                            _logInEvent.value = LogInEvent(true, sessionManager.fetchAuthToken())
                        }
                        Log.i("LoginActivity", loginResponse?.result?.token?:"brak tokena")
                    }
                }
            })
    }

    //TODO Add dependency injection instead
    fun initializeSessionManager(requireActivity: FragmentActivity) {
        sessionManager = SessionManager(requireActivity)
    }
}