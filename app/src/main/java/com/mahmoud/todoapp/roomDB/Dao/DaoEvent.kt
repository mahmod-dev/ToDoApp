package com.mahmoud.todoapp.roomDB.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mahmoud.todoapp.model.Event

@Dao
interface DaoEvent {
    @Query("SELECT * FROM event")
    suspend fun getAll(): List<Event>

    @Insert
    suspend fun insertAll(events: List<Event>)

    @Insert
    suspend fun insert(event: Event)

    @Delete
    suspend fun delete(event: Event)

}