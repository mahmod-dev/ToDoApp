package com.mahmoud.todoapp.util

import android.app.AlarmManager
import java.util.*

object AlarmUtils {
    const val REPEAT_DAILY = AlarmManager.INTERVAL_DAY
    const val REPEAT_WEEKLY =AlarmManager.INTERVAL_DAY * 7


    public fun getMonthlyDuration(cal: Calendar): Long {
        // get todays date
        // get current month
        var currentMonth: Int = cal.get(Calendar.MONTH)

        // move month ahead
        currentMonth++
        // check if has not exceeded threshold of december
        if (currentMonth > Calendar.DECEMBER) {
            // alright, reset month to jan and forward year by 1 e.g fro 2013 to 2014
            currentMonth = Calendar.JANUARY
            // Move year ahead as well
            cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1)
        }

        // reset calendar to next month
        cal.set(Calendar.MONTH, currentMonth)
        // get the maximum possible days in this month
        val maximumDay: Int = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

        // set the calendar to maximum day (e.g in case of fEB 28th, or leap 29th)
        cal.set(Calendar.DAY_OF_MONTH, maximumDay)
        return cal.timeInMillis // this is what you set as trigger point time i.e one month after
    }
}