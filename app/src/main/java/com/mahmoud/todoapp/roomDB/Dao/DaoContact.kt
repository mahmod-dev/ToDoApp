package com.mahmoud.todoapp.roomDB.Dao

import androidx.room.*
import com.mahmoud.todoapp.model.Contact
import com.mahmoud.todoapp.model.Event
import com.mahmoud.todoapp.model.Task

@Dao
interface DaoContact {
    @Query("SELECT * FROM contact")
    suspend fun getAll(): List<Contact>

    @Insert
    suspend fun insertAll(list: List<Contact>)

    @Insert
    suspend fun insert(contact: Contact)

}