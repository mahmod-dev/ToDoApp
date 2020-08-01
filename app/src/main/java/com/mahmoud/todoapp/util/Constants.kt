package com.mahmoud.todoapp.util

import android.content.Context
import android.text.format.DateUtils
import com.mahmoud.todoapp.R


object Constants {
    const val ERROR_DIALOG_REQUEST = 501
    const val PERMISSIONS_REQUEST_ENABLE_GPS = 502
    const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 503
    const val NOTIFICATION_EVENT = 22
    const val NOTIFICATION_TASK = 33
    const val NOTIFICATION_TIME = "notification_time"
    const val LANGUAGE = "lan"
    const val ENGLISH = "en"
    const val ARABIC = "ar"


    const val SECOND_MILLIS = 1000
    const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    const val NINE_MINUTE_MILLIS = 9 * MINUTE_MILLIS
    const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    const val DAY_MILLIS = 24 * HOUR_MILLIS
    const val AVERAGE_MONTH_IN_MILLIS = DateUtils.DAY_IN_MILLIS * 30


    public fun eventRepeatList(context: Context): Array<String> {
        return arrayOf(
            context.resources.getString(R.string.before_10_min),
            context.resources.getString(R.string.before_20_min),
            context.resources.getString(R.string.before_1_hour),
            context.resources.getString(R.string.before_2_hour),
            context.resources.getString(R.string.before_1_day),
            context.resources.getString(R.string.before_1_weak)
        )
    }

    val checkedItems = booleanArrayOf(true, false, false, false, false, false)

    public fun colors(): ArrayList<String> {
        val colors: ArrayList<String> = ArrayList()
        colors.add("#000000")
        colors.add("#4a148c")
        colors.add("#3e2723")
        colors.add("#1b5e20")
        colors.add("#ab003c")

        colors.add("#304ffe")
        colors.add("#00ff00")
        colors.add("#2196f3")
        colors.add("#4db6ac")
        colors.add("#ba68c8")

        colors.add("#006064")
        colors.add("#4dd0e1")
        colors.add("#1769aa")
        colors.add("#8D6E63")
        colors.add("#880e4f")

        colors.add("#ffde03")
        colors.add("#CDDC39")
        colors.add("#FF5722")
        colors.add("#ff0000")
        colors.add("#f50057")


        return colors
    }

}