package com.mahmoud.todoapp.roomDB.dao

import androidx.room.*
import com.mahmoud.todoapp.model.Task

@Dao
interface DaoTask {
    @Query("SELECT * FROM task ORDER BY date ")
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