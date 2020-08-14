package com.mahmoud.todoapp.util.dbUtil

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoud.todoapp.roomDB.DatabaseHelper
import com.mahmoud.todoapp.viewmodel.ContactViewModel
import com.mahmoud.todoapp.viewmodel.EventTaskViewModel
import com.mahmoud.todoapp.viewmodel.EventViewModel
import com.mahmoud.todoapp.viewmodel.TaskViewModel


class ViewModelFactory(private val dbHelper: DatabaseHelper, val application: Application) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            return EventViewModel(dbHelper) as T
        }

        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(dbHelper) as T
        }

        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            return ContactViewModel(dbHelper, application) as T
        }

        if (modelClass.isAssignableFrom(EventTaskViewModel::class.java)) {
            return EventTaskViewModel(dbHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }


}