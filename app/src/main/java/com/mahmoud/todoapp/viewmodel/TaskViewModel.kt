package com.mahmoud.todoapp.viewmodel

import androidx.lifecycle.*
import com.mahmoud.todoapp.roomDB.DatabaseHelper
import com.mahmoud.todoapp.model.Task
import com.mahmoud.todoapp.util.dbUtil.Resource
import kotlinx.coroutines.launch
import kotlin.Exception

class TaskViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {
    private val tasks = MutableLiveData<Resource<List<Task>>>()

    init {
        fetchTasks()
    }

    private fun fetchTasks() {
        viewModelScope.launch {
            tasks.postValue(Resource.loading(null))

            try {
                val tasksFromDB = dbHelper.getAllTasks()
                if (tasksFromDB.isEmpty()) {
                    //fetch data from Firestore
                } else
                    tasks.postValue(Resource.success(tasksFromDB))
            } catch (ex: Exception) {
                tasks.postValue(Resource.error(ex.message.toString(), null))

            }
        }
    }

    fun insertTask(task: Task) {
        viewModelScope.launch {
            try {
                tasks.postValue(Resource.loading(null))
                dbHelper.insertTask(task)
                val tasksFromDB = dbHelper.getAllTasks()
                tasks.postValue(Resource.success(tasksFromDB))
            } catch (ex: Exception) {
                tasks.postValue(Resource.error(ex.message.toString(), null))

            }

        }
    }


    fun insertAllTasks(tasksList: List<Task>) {
        viewModelScope.launch {
            try {
                tasks.postValue(Resource.loading(null))
                dbHelper.insertAllTasks(tasksList)
                val tasksFromDB = dbHelper.getAllTasks()
                tasks.postValue(Resource.success(tasksFromDB))
            } catch (ex: Exception) {
                tasks.postValue(Resource.error(ex.message.toString(), null))

            }

        }
    }


    fun getTasks(): LiveData<Resource<List<Task>>> {
        return tasks
    }

}