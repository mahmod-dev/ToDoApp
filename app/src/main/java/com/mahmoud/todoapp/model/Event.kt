package com.mahmoud.todoapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mahmoud.todoapp.util.StatusRing

@Entity
data class Event(

    var title: String = "", var details: String = "",
    var timeStart: String = "", var timeEnd: String = "",
    var dateStart: String = "", var dateEnd: String = "",
    var reminderRepeat: String = "", var ringtone: String = "",
    var longitude: Double = 0.0, var latitude: Double = 0.0,
    var createdDate: String = "", var isEnabledTone: Boolean = false,
    var image: String = "", var contactList: List<Contact>? = null
) {
    @PrimaryKey(autoGenerate = true)
    var eventId: Int = 0
}
