package com.mahmoud.todoapp.roomDB.Dao

import androidx.room.*
import com.mahmoud.todoapp.model.Event
import com.mahmoud.todoapp.model.Task

@Dao
interface DaoTask {
    @Query("SELECT * FROM task")
    suspend fun getAll(): List<Task>

    @Insert
    suspend fun insertAll(list: List<Task>)

    @Insert
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

}