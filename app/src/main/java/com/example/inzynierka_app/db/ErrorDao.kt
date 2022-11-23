package com.example.inzynierka_app.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ErrorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gripperError: GripperError)

    @Query("SELECT * FROM error_table ORDER BY error_date DESC")
    fun getAllErrorsSortedByDate(): LiveData<List<GripperError>>

    @Query("DELETE FROM error_table")
    suspend fun delete()

}