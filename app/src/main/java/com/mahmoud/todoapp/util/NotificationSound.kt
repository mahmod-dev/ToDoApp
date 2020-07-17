package com.mahmoud.todoapp.util

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager

open class NotificationSound private constructor(var context: Context) {
    var r: Ringtone? = null

     fun getRingToneInstance(): Ringtone? {


        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)

        if (r == null) {
            r = RingtoneManager.getRingtone(context, notification)
        }

        return r
    }



    public fun Ringtone.startRing() {
        if (r != null) {

            if (r!!.isPlaying) {
                r!!.stop()
            }
            r!!.play()
        }
    }

    public fun Ringtone.stopRing() {
        if (r != null) {

            if (r!!.isPlaying) {
                r!!.stop()
            }
        }
    }

    companion object : SingletonHolder<NotificationSound, Context>(::NotificationSound)

}