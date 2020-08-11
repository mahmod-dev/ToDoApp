package com.mahmoud.todoapp.util

import android.content.Context
import android.text.format.DateFormat
import android.text.format.DateUtils
import android.widget.TextView
import com.mahmoud.todoapp.R
import com.mahmoud.todoapp.util.Constants.AVERAGE_MONTH_IN_MILLIS
import com.mahmoud.todoapp.util.Constants.DAY_MILLIS
import com.mahmoud.todoapp.util.Constants.HOUR_MILLIS
import com.mahmoud.todoapp.util.Constants.MINUTE_MILLIS
import java.text.SimpleDateFormat
import java.util.*


object DateHelper {

    fun getFormatTime(format: String = "hh:mm a") =
        DateFormat.format(format, Date()).toString()

    fun getFormatTime(format: String = "hh:mm a", millSecond: Long) =
        DateFormat.format(format, millSecond).toString()


    fun getFormatDate(format: String = "yyyy-MMM-dd") =
        DateFormat.format(format, Date()).toString()

    fun getFormatDate(format: String = "yyyy-MMM-dd", date: Calendar) =
        DateFormat.format(format, date).toString()

    fun getFormatDate(format: String = "yyyy-MMM-dd", date: Date) =
        DateFormat.format(format, date).toString()


    fun getFormatDateTime(format: String = "yyyy-MM-dd hh:mm:ss a") =
        DateFormat.format(format, Date()).toString()


    public fun getTimeAgo(time: Long): String? {
        var time = time
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000
        }
        val now = System.currentTimeMillis()
        if (time > now || time <= 0) {
            return null
        }

        // TODO: localize
        val diff = now - time
        return when {
            diff < MINUTE_MILLIS -> {
                "just now"
            }
            diff < 2 * MINUTE_MILLIS -> {
                "a minute ago"
            }
            diff < 50 * MINUTE_MILLIS -> {
                (diff / MINUTE_MILLIS).toString() + " minutes ago"
            }
            diff < 90 * MINUTE_MILLIS -> {
                "an hour ago"
            }
            diff < 24 * HOUR_MILLIS -> {
                (diff / HOUR_MILLIS).toString() + " hours ago"
            }
            diff < 48 * HOUR_MILLIS -> {
                "yesterday"
            }
            else -> {
                (diff / DAY_MILLIS).toString() + " days ago"
            }
        }
    }


    fun getRelationTime(time: Long): String {
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
        } else return when {
            delta <= AVERAGE_MONTH_IN_MILLIS -> {
                (delta / DateUtils.WEEK_IN_MILLIS).toInt().toString() + " weeks(s) ago"
            }
            delta <= DateUtils.YEAR_IN_MILLIS -> {
                (delta / AVERAGE_MONTH_IN_MILLIS).toInt().toString() + " month(s) ago"
            }
            else -> {
                (delta / DateUtils.YEAR_IN_MILLIS).toInt().toString() + " year(s) ago"
            }
        }

        return DateUtils.getRelativeTimeSpanString(time, now, resolution).toString()
    }


    public fun updateDateText(c: Calendar, textView: TextView) {
        val myFormat = "dd MMM" //In which you need put here
        var locale = Locale("ar", "SA")
        if (Locale.getDefault().language == "en") {
            locale = Locale.ENGLISH
        }
        val sdf = SimpleDateFormat(myFormat, locale)


        textView.text = sdf.format(c.time)

    }

    public fun updateTimeText(c: Calendar, textView: TextView) {
        val timeText =
            java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT).format(c.time)
        textView.text = timeText
    }

    public fun timeToString(millis: Long): String {
        return java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT).format(millis)
    }


    public fun dateToString(context: Context,millis: Long, format: String = "dd MMM"): String {

        if (DateUtils.isToday(millis)) {
            return context.getString(R.string.today)
        }
        if (DateUtils.isToday(millis - DateUtils.DAY_IN_MILLIS)) {
            return context.getString(R.string.tomorrow)
        }
        if (DateUtils.isToday(millis + DateUtils.DAY_IN_MILLIS)) {
            return context.getString(R.string.yesterday)
        }
        var locale = Locale("ar", "SA")
        if (Locale.getDefault().language == "en") {
            locale = Locale.ENGLISH
        }
        return SimpleDateFormat(format, locale).format(millis)
    }

    fun isTimeAfterOrEqual(startTime: Date?, endTime: Date?): Boolean? {
        return endTime?.before(startTime)?.or((endTime.compareTo(startTime) == 0))
    }




}