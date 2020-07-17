package com.mahmoud.todoapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mahmoud.todoapp.util.Constants.NOTIFICATION_EVENT
import com.mahmoud.todoapp.util.NotificationHelper


class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {

        val notificationHelper = NotificationHelper(context)
        if (intent?.extras?.getBoolean("cancel") == true) {
            notificationHelper.getManager()?.cancel(NOTIFICATION_EVENT)
        }

    }
}
