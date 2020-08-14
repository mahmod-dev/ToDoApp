package com.mahmoud.todoapp.model

import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mahmoud.todoapp.util.StatusRing

data class EventTask(

    var event: Event? = null, var task: Task? = null,
    var type : Int = 0 // 0 event,  1 task

)