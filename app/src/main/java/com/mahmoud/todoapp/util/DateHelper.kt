package com.mahmoud.todoapp.util

import android.text.format.DateFormat
import android.text.format.DateUtils
import com.mahmoud.todoapp.util.Constants.AVERAGE_MONTH_IN_MILLIS
import com.mahmoud.todoapp.util.Constants.DAY_MILLIS
import com.mahmoud.todoapp.util.Constants.HOUR_MILLIS
import com.mahmoud.todoapp.util.Constants.MINUTE_MILLIS
import jp.wasabeef.richeditor.Utils.getCurrentTime
import java.util.*


object DateHelper {

     fun getFormatTime(format: String = "hh:mm a") =
        DateFormat.format(format, Date()).toString()

     fun getFormatDate(format: String = "yyyy-MMM-dd") =
        DateFormat.format(format, Date()).toString()

     fun getFormatDateTime(format: String = "yyyy-MM-dd hh:mm:ss a") =
        DateFormat.format(format, Date()).toString()


   public fun getTimeAgo(time: Long): String? {
        var time = time
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000
        }
        val now = getCurrentTime()
        if (time > now || time <= 0) {
            return null
        }

        // TODO: localize
        val diff = now - time
        return if (diff < MINUTE_MILLIS) {
            "just now"
        } else if (diff < 2 * MINUTE_MILLIS) {
            "a minute ago"
        } else if (diff < 50 * MINUTE_MILLIS) {
            (diff / MINUTE_MILLIS).toString() + " minutes ago"
        } else if (diff < 90 * MINUTE_MILLIS) {
            "an hour ago"
        } else if (diff < 24 * HOUR_MILLIS) {
            (diff / HOUR_MILLIS).toString() + " hours ago"
        } else if (diff < 48 * HOUR_MILLIS) {
            "yesterday"
        } else {
            (diff / DAY_MILLIS).toString()+ " days ago"
        }
    }


    private fun getRelationTime(time: Long): String? {
        val now = Date().time
        val delta = now - time
        val resolution: Long
        resolution = if (delta <= DateUtils.MINUTE_IN_MILLIS) {
            DateUtils.SECOND_IN_MILLIS
        } else if (delta <= DateUtils.HOUR_IN_MILLIS) {
            DateUtils.MINUTE_IN_MILLIS
        } else if (delta <= DateUtils.DAY_IN_MILLIS) {
            DateUtils.HOUR_IN_MILLIS
        } else if (delta <= DateUtils.WEEK_IN_MILLIS) {
            DateUtils.DAY_IN_MILLIS
        } else return if (delta <= AVERAGE_MONTH_IN_MILLIS) {
            Integer.toString((delta / DateUtils.WEEK_IN_MILLIS).toInt()) + " weeks(s) ago"
        } else if (delta <= DateUtils.YEAR_IN_MILLIS) {
            Integer.toString((delta / AVERAGE_MONTH_IN_MILLIS).toInt()) + " month(s) ago"
        } else {
            Integer.toString((delta / DateUtils.YEAR_IN_MILLIS).toInt()) + " year(s) ago"
        }
        return DateUtils.getRelativeTimeSpanString(time, now, resolution).toString()
    }
}