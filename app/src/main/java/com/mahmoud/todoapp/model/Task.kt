package com.mahmoud.todoapp.model

import com.mahmoud.todoapp.util.StatusRing
import com.mahmoud.todoapp.util.TasksType

data class Task(
    var title: String = "", var details: String = "",
    var time: String = "", var date: String = "",
    var longitude: Double = 0.0, var latitude: Double = 0.0,
    var createdDate: String = "", var isEnabled: Boolean = false,
    var statusRing: StatusRing = StatusRing.Unknown, var image: String = "",
    var tasksType: TasksType = TasksType.Daily

)
