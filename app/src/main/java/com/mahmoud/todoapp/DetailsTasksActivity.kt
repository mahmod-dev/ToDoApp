package com.mahmoud.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat

class DetailsTasksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_tasks)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary);
    }
}
