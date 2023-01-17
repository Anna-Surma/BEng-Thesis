package com.example.inzynierka_app.other

import android.os.SystemClock

class Timer {
    var offset: Long = 0

    fun setBaseTime(): Long {
        return SystemClock.elapsedRealtime() - offset
    }

    fun getElapsedRealtime(): Long {
        return SystemClock.elapsedRealtime()
    }
}