package com.mahmoud.todoapp.roomDB.dao

import androidx.room.*
import com.mahmoud.todoapp.model.Contact

@Dao
interface DaoContact {
    @Query("SELECT * FROM contact ORDER BY name ASC ")
    suspend fun getAll(): List<Contact>

    @Query("SELECT * FROM contact WHERE name like '%' || :name || '%' ORDER BY name ASC ")
    suspend fun getName(name:String): List<Contact>

    @Insert(onConflict =OnConflictStrategy.IGNORE )
    suspend fun insertAll(list: List<Contact>)

    @Insert
    suspend fun insert(contact: Contact)

}