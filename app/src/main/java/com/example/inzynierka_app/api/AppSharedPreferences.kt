package com.example.inzynierka_app.api

import android.content.SharedPreferences
import javax.inject.Inject

class AppSharedPreferences @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    fun saveAuthToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(ACCESS_TOKEN_KEY, token)
        editor.apply()
    }

    fun fetchAuthToken(): String? = sharedPreferences.getString(ACCESS_TOKEN_KEY, null)

    companion object {
        const val ACCESS_TOKEN_KEY = "access_token"
        const val SHARED_PREFS = "APP_SHARED_PREFS"
    }
}