package com.example.inzynierka_app.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "error_table")
data class GripperError(
    @ColumnInfo(name = "error_date")
    var errorDate: String = "",
    @ColumnInfo(name = "error_name")
    var errorName: String = "",
    @ColumnInfo(name = "error_desc")
    var errorDesc: String = "",
) {
    @PrimaryKey(autoGenerate = true)
    var errorId: Long = 0L
}
