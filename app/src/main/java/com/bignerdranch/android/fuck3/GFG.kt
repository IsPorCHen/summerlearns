package com.bignerdranch.android.fuck3

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GFG(
    @PrimaryKey val courseID: Int,
    @ColumnInfo(name = "courseName") val name: String?,
    @ColumnInfo(name = "courseID") val email: String?,
    @ColumnInfo(name = "coursePrice") val avatar: String?
)
