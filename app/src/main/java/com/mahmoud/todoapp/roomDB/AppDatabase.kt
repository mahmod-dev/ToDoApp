package com.mahmoud.todoapp.roomDB

import android.content.Context
import androidx.room.*
import com.mahmoud.todoapp.model.Contact
import com.mahmoud.todoapp.roomDB.Dao.DaoEvent
import com.mahmoud.todoapp.model.Event
import com.mahmoud.todoapp.model.Task
import com.mahmoud.todoapp.roomDB.Dao.DaoContact
import com.mahmoud.todoapp.roomDB.Dao.DaoTask

@Database(entities = [Event::class, Task::class, Contact::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun daoEvent(): DaoEvent
    abstract fun daoTask(): DaoTask
    abstract fun daoContact(): DaoContact

    companion object {
        private var INSTANCE: AppDatabase? = null


        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildRoomDB(context)
                }
            }

            return INSTANCE!!
        }

        private fun buildRoomDB(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "Todo.db"
            ).build()
        }
    }
}