package com.mahmoud.todoapp.roomDB
import com.mahmoud.db_with_coroutine.roomDB.DatabaseHelper
import com.mahmoud.todoapp.model.Event

class DatabaseHelperImp(private val appDatabase: AppDatabase) : DatabaseHelper {
    override suspend fun getAllEvents(): List<Event> {
        return appDatabase.daoEvent().getAll();
    }

    override suspend fun insertAllEvents(list: List<Event>) {
        return appDatabase.daoEvent().insertAll(list)
    }

    override suspend fun insertEvents(event: Event) {
        return appDatabase.daoEvent().insert(event)
    }

    override suspend fun deleteEvents(event: Event) {
        return appDatabase.daoEvent().delete(event)
    }
}