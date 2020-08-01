package com.mahmoud.todoapp

import android.app.Notification
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mahmoud.todoapp.util.Constants
import com.mahmoud.todoapp.util.NotificationHelper


class TestActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val notificationHelper = NotificationHelper(this)
        val nb = notificationHelper.createNotification("titttle", true)
        //notificationHelper.getManager()!!.notify(Constants.NOTIFICATION_EVENT, nb.build())
    }
}
