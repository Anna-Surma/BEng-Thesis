package com.example.inzynierka_app.model

import android.content.Context
import android.content.SharedPreferences
import com.example.inzynierka_app.R

class SessionManager (context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
    }

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }
}
//class SessionManager (context: Context) {
//    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
//
//    companion object {
//        const val USER_TOKEN = "token"
//    }
//
//    fun saveAuthToken(token: String) {
//        val editor = prefs.edit()
//        editor.putString(USER_TOKEN, token)
//        editor.apply()
//    }
//
//    fun fetchAuthToken(): String? {
//        return prefs.getString(USER_TOKEN, null)
//    }
//}