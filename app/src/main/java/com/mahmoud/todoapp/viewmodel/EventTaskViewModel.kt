package com.mahmoud.todoapp.viewmodel

import androidx.lifecycle.*
import com.mahmoud.todoapp.roomDB.DatabaseHelper
import com.mahmoud.todoapp.model.Event
import com.mahmoud.todoapp.model.EventTask
import com.mahmoud.todoapp.model.Task
import com.mahmoud.todoapp.util.dbUtil.Resource
import kotlinx.coroutines.launch
import kotlin.Exception

class EventTaskViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {

    private var eventTask = MutableLiveData<Resource<List<EventTask>>>()
    private var list : ArrayList<EventTask>? = ArrayList()

    init {
        fetchEvents()
        fetchTasks()
        postData()
    }

    private fun fetchEvents() {
        viewModelScope.launch {
          eventTask.postValue(Resource.loading(null))

            try {
                val eventFromDB = dbHelper.getAllEvents()
                for (i in dbHelper.getAllEvents().indices) {
                    val model = EventTask()

                    model.event =  dbHelper.getAllEvents().get(i)
                    model.type = 0
                    list!!.add(model)
                }
                if (eventFromDB.isEmpty()) {
                    //fetch data from Firestore
                } else{
                    eventTask.postValue(Resource.success(list))

                }
            } catch (ex: Exception) {
                eventTask.postValue(Resource.error(ex.message.toString(), null))

            }
        }
    }





    private fun fetchTasks() {
        viewModelScope.launch {
            eventTask.postValue(Resource.loading(null))


            try {
                val tasksFromDB = dbHelper.getAllTasks()


                for (i in dbHelper.getAllTasks().indices){
                    val model = EventTask()

                    model.task = dbHelper.getAllTasks().get(i)
                    model.type=1
                    list!!.add(model)

                }
                if (tasksFromDB.isEmpty()) {
                    //fetch data from Firestore
                } else{
                    eventTask.postValue(Resource.success(list))


                }
            } catch (ex: Exception) {
                eventTask.postValue(Resource.error(ex.message.toString(), null))
            }
        }
    }

    fun getEventTask(): LiveData<Resource<List<EventTask>>> {
        return eventTask
    }

    private fun postData(){
   //     eventTask.postValue(Resource.success(list))

    }
}