package com.mahmoud.todoapp.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Contact(
    var name: String? = null,
    var number: String? = null,
    @Ignore
    var isSelected: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var contactId: Int = 0

}