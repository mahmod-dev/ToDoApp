package com.mahmoud.todoapp.model

class Contact {
    var name: String? = null
    var number: String? = null
    var isSelected = false

    constructor(
        name: String?,
        number: String?,
        selected: Boolean
    ) {
        this.name = name
        this.number = number
        isSelected = selected
    }

    constructor() {}

}