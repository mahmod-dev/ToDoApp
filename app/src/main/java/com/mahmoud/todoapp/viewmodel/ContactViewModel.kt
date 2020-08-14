package com.mahmoud.todoapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import co.tiagoaguiar.recyclermasterjava.util.Helper
import com.mahmoud.todoapp.roomDB.DatabaseHelper
import com.mahmoud.todoapp.model.Contact
import com.mahmoud.todoapp.util.dbUtil.Resource
import kotlinx.coroutines.*
import kotlin.Exception

class ContactViewModel(private val dbHelper: DatabaseHelper, application: Application) :
    AndroidViewModel(application) {
    private val contacts = MutableLiveData<Resource<List<Contact>>>()

    init {
        fetchContacts(application.applicationContext)
    }

    private fun fetchContacts(context: Context) {
        viewModelScope.launch {
            contacts.postValue(Resource.loading(null))

            try {
                val contactFromDB = dbHelper.getAllContacts()
                if (contactFromDB.isEmpty()) {

                    CoroutineScope(Dispatchers.IO).launch {
                        async {
                            insertAllContact(Helper.getContactList(context))

                        }.await()
                    }

                } else{
                    contacts.postValue(Resource.success(contactFromDB))
                    CoroutineScope(Dispatchers.IO).launch {
                        async {
                            insertAllContact(Helper.getContactList(context))

                        }.await()
                    }
                }
            } catch (ex: Exception) {
                contacts.postValue(Resource.error(ex.message.toString(), null))

            }
        }
    }

     fun fetchContactName(contactName: String) {
        viewModelScope.launch {
            contacts.postValue(Resource.loading(null))

            try {
                val contactFromDB = dbHelper.getWhereName(contactName)
                if (contactFromDB.isNotEmpty()){
                    contacts.postValue(Resource.success(contactFromDB))
                }else
                    contacts.postValue(Resource.error("Not found contacts!", null))


            } catch (ex: Exception) {
                contacts.postValue(Resource.error(ex.message.toString(), null))

            }
        }
    }

    fun insertContact(contact: Contact) {
        viewModelScope.launch {
            try {
                contacts.postValue(Resource.loading(null))
                dbHelper.insertContact(contact)
                val contactFromDB = dbHelper.getAllContacts()
                contacts.postValue(Resource.success(contactFromDB))
            } catch (ex: Exception) {
                contacts.postValue(Resource.error(ex.message.toString(), null))

            }

        }
    }


    fun insertAllContact(contactsList: ArrayList<Contact>) {
        viewModelScope.launch {
            try {
                contacts.postValue(Resource.loading(null))
                dbHelper.insertAllContacts(contactsList)
                val contactFromDB = dbHelper.getAllContacts()
                contacts.postValue(Resource.success(contactFromDB))
            } catch (ex: Exception) {
                contacts.postValue(Resource.error(ex.message.toString(), null))

            }

        }
    }


    fun getContacts(): LiveData<Resource<List<Contact>>> {
        return contacts
    }


}