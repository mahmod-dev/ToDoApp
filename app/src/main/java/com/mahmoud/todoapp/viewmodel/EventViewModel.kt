package com.mahmoud.todoapp.viewmodel

import androidx.lifecycle.*
import com.mahmoud.db_with_coroutine.roomDB.DatabaseHelper
import com.mahmoud.todoapp.model.Event
import com.mahmoud.todoapp.util.dbUtil.Resource
import kotlinx.coroutines.launch
import kotlin.Exception

class EventViewModel(private val dbHelper: DatabaseHelper) : ViewModel() {
    private val events = MutableLiveData<Resource<List<Event>>>()

    init {
        fetchEvents()
    }

    private fun fetchEvents() {
        viewModelScope.launch {
            events.postValue(Resource.loading(null))

            try {
                val eventFromDB = dbHelper.getAllEvents()
                if (eventFromDB.isEmpty()) {
                    //fetch data from Firestore
                } else
                    events.postValue(Resource.success(eventFromDB))
            } catch (ex: Exception) {
                events.postValue(Resource.error(ex.message.toString(), null))

            }
        }
    }

    fun insertEvents(event: Event) {
        viewModelScope.launch {
            try {
                events.postValue(Resource.loading(null))
                dbHelper.insertEvents(event)
                val eventFromDB = dbHelper.getAllEvents()
                events.postValue(Resource.success(eventFromDB))
            } catch (ex: Exception) {
                events.postValue(Resource.error(ex.message.toString(), null))

            }

        }
    }


    fun insertAllEvents(eventsList: List<Event>) {
        viewModelScope.launch {
            try {
                events.postValue(Resource.loading(null))
                dbHelper.insertAllEvents(eventsList)
                val eventFromDB = dbHelper.getAllEvents()
                events.postValue(Resource.success(eventFromDB))
            } catch (ex: Exception) {
                events.postValue(Resource.error(ex.message.toString(), null))

            }

        }
    }


    fun getEvents(): LiveData<Resource<List<Event>>> {
        return events
    }

}