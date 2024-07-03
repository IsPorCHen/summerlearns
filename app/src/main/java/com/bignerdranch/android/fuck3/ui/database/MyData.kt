package com.bignerdranch.android.fuck3.ui.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_data_table")
data class MyData(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val image: ByteArray,
    val name: String,
    val surname: String,
    val group: String
)
