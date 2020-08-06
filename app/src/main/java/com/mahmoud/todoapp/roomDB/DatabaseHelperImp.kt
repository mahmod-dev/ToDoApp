package com.mahmoud.todoapp.roomDB

import com.mahmoud.db_with_coroutine.roomDB.DatabaseHelper
import com.mahmoud.todoapp.model.Contact
import com.mahmoud.todoapp.model.Event
import com.mahmoud.todoapp.model.Task

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

    override suspend fun updateEvents(event: Event) {
        return appDatabase.daoEvent().update(event)
    }

    override suspend fun getAllTasks(): List<Task> {
        return appDatabase.daoTask().getAll()
    }

    override suspend fun insertAllTasks(list: List<Task>) {
        return appDatabase.daoTask().insertAll(list)

    }

    override suspend fun insertTask(task: Task) {
        return appDatabase.daoTask().insert(task)

    }

    override suspend fun deleteTask(task: Task) {
        return appDatabase.daoTask().delete(task)

    }

    override suspend fun updateTask(task: Task) {
        return appDatabase.daoTask().update(task)

    }

    override suspend fun getAllContacts(): List<Contact> {
        return appDatabase.daoContact().getAll()
    }

    override suspend fun insertAllContacts(list: List<Contact>) {
        return appDatabase.daoContact().insertAll(list)
    }

    override suspend fun insertContact(contact: Contact) {
        return appDatabase.daoContact().insert(contact)
    }

}