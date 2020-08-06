package com.mahmoud.todoapp.roomDB.Dao

import androidx.room.*
import com.mahmoud.todoapp.model.Event
import com.mahmoud.todoapp.model.Task

@Dao
interface DaoEvent {
    @Query("SELECT * FROM event")
    suspend fun getAll(): List<Event>

    @Insert
    suspend fun insertAll(list: List<Event>)

    @Insert
    suspend fun insert(event: Event)

    @Update
    suspend fun update(event: Event)

    @Delete
    suspend fun delete(event: Event)

}