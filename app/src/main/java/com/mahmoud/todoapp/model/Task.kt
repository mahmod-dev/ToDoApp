package com.mahmoud.todoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.mahmoud.todoapp.roomDB.DataConverter
import com.mahmoud.todoapp.util.StatusRing
import com.mahmoud.todoapp.util.TasksType
import java.io.Serializable

@Entity
data class Task(
    var title: String = "", var details: String = "",
    var time: String = "", var date: String = "",
    var ringtone: String = "",
    var longitude: Double = 0.0, var latitude: Double = 0.0,
    var createdDate: String = "", var isEnabled: Boolean = false,
    var statusRing: StatusRing = StatusRing.Unknown, var image: String = "",
    var tasksType: TasksType = TasksType.Daily,
    var reminderRepeat: String = ""

): Serializable  {

    @PrimaryKey(autoGenerate = true)
    var taskId: Int = 0
}
