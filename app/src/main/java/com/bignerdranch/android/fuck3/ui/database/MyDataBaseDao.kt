package com.bignerdranch.android.fuck3.ui.database

import android.provider.ContactsContract.Data
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MyDataBaseDao {
    @Insert
    suspend fun insertMyData(myData: MyData)

    @Update
    suspend fun updateMyData(myData: MyData)

    @Query("SELECT * FROM my_data_table WHERE name = :name AND surname = :surname")
    suspend fun getUserByNameAndSurname(name: String, surname: String): MyData?

    @Query("SELECT * FROM my_data_table WHERE id = :userId")
    suspend fun getUserById(userId: Int): MyData?
}
