package com.mahmoud.todoapp.util.dbUtil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoud.db_with_coroutine.roomDB.DatabaseHelper
import com.mahmoud.todoapp.viewmodel.EventViewModel


class ViewModelFactory(private val dbHelper: DatabaseHelper) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            return EventViewModel(dbHelper) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }

}