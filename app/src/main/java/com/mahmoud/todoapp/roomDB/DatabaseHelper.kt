package com.mahmoud.db_with_coroutine.roomDB

import com.mahmoud.todoapp.model.Event

interface DatabaseHelper {

    suspend fun getAllEvents(): List<Event>

    suspend fun insertAllEvents(list: List<Event>)

    suspend fun insertEvents(event: Event)

    suspend fun deleteEvents(event: Event)
}