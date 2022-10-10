package com.example.inzynierka_app.api

import javax.inject.Inject

class SessionManager @Inject constructor(
    private val pref: AppSharedPreferences
) {
    fun fetchAuthToken(): String? = pref.fetchAuthToken()

    fun saveAuthToken(token: String) {
        pref.saveAuthToken(token)
    }
}