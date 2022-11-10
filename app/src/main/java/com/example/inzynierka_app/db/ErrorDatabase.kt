package com.example.inzynierka_app.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [GripperError::class],
    version = 1, exportSchema = false
)

abstract class ErrorDatabase : RoomDatabase() {
    abstract fun getErrorDao(): ErrorDao
}