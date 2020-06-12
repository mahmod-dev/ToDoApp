package com.mahmoud.todoapp.model

import com.mahmoud.todoapp.util.StatusRing

data class Event(
    var title: String ="", var details: String = "",
    var timeStart: String = "", var timeEnd: String = "",
    var dateStart: String = "", var dateEnd: String = "",
    var longitude: Double = 0.0, var latitude: Double = 0.0,
    var createdDate: String = "", var isEnabled:Boolean = false,
    var statusRing: StatusRing = StatusRing.Unknown, var image: String = "",
    var contacts: MutableList<Contact>? = null


)
