package com.mahmoud.db_with_coroutine.roomDB

import androidx.room.Insert
import androidx.room.Query
import com.mahmoud.todoapp.model.Contact
import com.mahmoud.todoapp.model.Event
import com.mahmoud.todoapp.model.Task

interface DatabaseHelper {

    suspend fun getAllEvents(): List<Event>

    suspend fun insertAllEvents(list: List<Event>)

    suspend fun insertEvents(event: Event)

    suspend fun deleteEvents(event: Event)

    suspend fun updateEvents(event: Event)


    suspend fun getAllTasks(): List<Task>

    suspend fun insertAllTasks(list: List<Task>)

    suspend fun insertTask(task: Task)

    suspend fun deleteTask(task: Task)

    suspend fun updateTask(task: Task)


    suspend fun getAllContacts(): List<Contact>

    suspend fun insertAllContacts(list: List<Contact>)

    suspend fun insertContact(contact: Contact)
}