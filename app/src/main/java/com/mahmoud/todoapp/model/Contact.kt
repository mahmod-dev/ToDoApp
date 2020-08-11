package com.mahmoud.todoapp.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
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
    @PrimaryKey
    @NonNull
    var contactId: String? = null

}