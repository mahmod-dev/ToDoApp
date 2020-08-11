package com.mahmoud.todoapp.model

import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mahmoud.todoapp.util.StatusRing

@Entity
data class Event(

    var title: String = "", var details: String = "",
    var timeStart: Long = 0, var timeEnd: Long = 0,
    var dateStart: Long = 0, var dateEnd: Long = 0,
    var reminderRepeat: String = "", var ringtone: String = "",
    var longitude: Double = 0.0, var latitude: Double = 0.0,
    var createdDate: Long = System.currentTimeMillis(), var isEnabledTone: Boolean = false,

    var imagePath: String = "",
    var contactList: List<Contact>? = null
) {
    @PrimaryKey(autoGenerate = true)
    var eventId: Int = 0
}
