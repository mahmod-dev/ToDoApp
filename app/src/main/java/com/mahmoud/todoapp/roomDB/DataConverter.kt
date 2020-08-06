package com.mahmoud.todoapp.roomDB

import android.media.Ringtone
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mahmoud.todoapp.model.Contact
import com.mahmoud.todoapp.util.StatusRing
import com.mahmoud.todoapp.util.TasksType

class DataConverter {
    @TypeConverter
    fun toContact(json: String): List<Contact> {
        val type = object : TypeToken<List<Contact>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromContact(contactList: List<Contact>): String {
        val type = object: TypeToken<List<Contact>>() {}.type
        return Gson().toJson(contactList, type)
    }

    @TypeConverter
    fun fromTasksTypeToStr(tasksType: TasksType): String {
        return Gson().toJson(tasksType)
    }


    @TypeConverter
    fun toTasksType(json: String): TasksType {
        val type = object: TypeToken<TasksType>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromStatusRing(status: StatusRing): String {
        return Gson().toJson(status)
    }


    @TypeConverter
    fun toStatusRing(json: String): StatusRing {
        val type = object: TypeToken<StatusRing>() {}.type
        return Gson().fromJson(json, type)
    }
}
