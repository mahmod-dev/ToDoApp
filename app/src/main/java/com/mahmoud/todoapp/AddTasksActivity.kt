package com.mahmoud.todoapp

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import co.tiagoaguiar.recyclermasterjava.util.Helper
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mahmoud.todoapp.util.Constants
import com.mahmoud.todoapp.util.DateHelper
import com.mahmoud.todoapp.util.DateHelper.updateDateText
import com.mahmoud.todoapp.util.DateHelper.updateTimeText
import kotlinx.android.synthetic.main.activity_add_tasks.*
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit

class AddTasksActivity : AppCompatActivity() {
    private var calendarDate: Calendar? = null
    private var calendarTime: Calendar? = null
    private var startDate: Date? = null
    private var startTime: Date? = null

    var isEnable = false
    var repeatType: String? = null
    var ringType: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tasks)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        calendarDate = Calendar.getInstance()
        calendarTime = Calendar.getInstance()
        isEnable = true

        handleTaskType()
        handleBell()

        imgAddTask.setOnClickListener {
            addImage(this)
        }

        carAdd.setOnClickListener {
            addImage(this)
        }

        imgAdd.setOnClickListener {
            addImage(this)
        }

        imgArrowBackTask.setOnClickListener {
            finish()
        }

        containerRingtone.setOnClickListener {
            handleTaskRing()
        }

        containerReminder.setOnClickListener {
            handleTaskRepetition()
        }

        tvTaskTime.setOnClickListener {
            setTime()
        }

        tvTaskDate.setOnClickListener {
            setDate()
        }


    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.data
            imgAddTask.setImageURI(fileUri)
            imgAdd.visibility = View.GONE
            carAdd.visibility = View.GONE
            //You can get File object from intent
            val file: File = ImagePicker.getFile(data)!!

            //You can also get File Path from intent
            val filePath: String = ImagePicker.getFilePath(data)!!
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addImage(activity: Activity) {
        Helper.selectImageDialog(activity)
    }

    private fun handleBell() {
        fabBellTask.setOnClickListener {
            if (isEnable) {
                isEnable = false
                fabBellTask.setImageResource(R.drawable.ic_bell_cancel)
                Toast.makeText(this, "alarm disabled", Toast.LENGTH_LONG).show()

            } else {

                Toast.makeText(this, "alarm enabled", Toast.LENGTH_LONG).show()
                isEnable = true
                fabBellTask.setImageResource(R.drawable.ic_bell)
            }
        }

    }

    private fun handleTaskType() {
        fabDaily.setOnClickListener {
            tvDaily.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorPrimary
                )
            )


            tvFriends.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.gray_light
                )
            )

            tvHome.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.gray_light
                )
            )

            tvWork.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.gray_light
                )
            )

/////////////////////////////////////////////////////////////////////
            fabDaily.colorNormal = ContextCompat.getColor(
                this,
                R.color.colorPrimary
            )
            fabWork.colorNormal = ContextCompat.getColor(
                this,
                R.color.colorPrimary_light
            )
            fabHome.colorNormal = ContextCompat.getColor(
                this,
                R.color.colorPrimary_light
            )

            fabFriends.colorNormal = ContextCompat.getColor(
                this,
                R.color.colorPrimary_light
            )

        }

        fabWork.setOnClickListener {
            tvWork.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorPrimary
                )
            )

            tvDaily.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.gray_light
                )
            )

            tvHome.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.gray_light
                )
            )

            tvFriends.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.gray_light
                )
            )

            /////////////////////////////////////////////

            fabDaily.colorNormal = ContextCompat.getColor(
                this,
                R.color.colorPrimary_light
            )
            fabWork.colorNormal = ContextCompat.getColor(
                this,
                R.color.colorPrimary
            )
            fabHome.colorNormal = ContextCompat.getColor(
                this,
                R.color.colorPrimary_light
            )

            fabFriends.colorNormal = ContextCompat.getColor(
                this,
                R.color.colorPrimary_light
            )
        }
        fabHome.setOnClickListener {
            tvHome.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorPrimary
                )
            )

            tvWork.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.gray_light
                )
            )

            tvDaily.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.gray_light
                )
            )

            tvFriends.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.gray_light
                )
            )

            /////////////////////////////////

            fabDaily.colorNormal = ContextCompat.getColor(
                this,
                R.color.colorPrimary_light
            )
            fabWork.colorNormal = ContextCompat.getColor(
                this,
                R.color.colorPrimary_light
            )
            fabHome.colorNormal = ContextCompat.getColor(
                this,
                R.color.colorPrimary
            )

            fabFriends.colorNormal = ContextCompat.getColor(
                this,
                R.color.colorPrimary_light
            )
        }
        fabFriends.setOnClickListener {
            tvFriends.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorPrimary
                )
            )

            tvHome.setTextColor(ContextCompat.getColor(
                    this,
                    R.color.gray_light)
            )

            tvWork.setTextColor(ContextCompat.getColor(
                    this,
                    R.color.gray_light
                )
            )

            tvDaily.setTextColor(ContextCompat.getColor(
                    this,
                    R.color.gray_light
                )
            )
            /////////////////////////////////////////////


            fabDaily.colorNormal = ContextCompat.getColor(
                this,
                R.color.colorPrimary_light
            )
            fabWork.colorNormal = ContextCompat.getColor(
                this,
                R.color.colorPrimary_light
            )
            fabHome.colorNormal = ContextCompat.getColor(
                this,
                R.color.colorPrimary_light
            )

            fabFriends.colorNormal = ContextCompat.getColor(
                this,
                R.color.colorPrimary
            )


        }


    }

    private fun handleTaskRepetition() {
        repeatType = resources.getString(R.string.without_repeat)

        val items = arrayOf(
            resources.getString(R.string.without_repeat),
            resources.getString(R.string.daily),
            resources.getString(R.string.weekly),
            resources.getString(R.string.monthly),
            resources.getString(R.string.yearly)
        )

        val title = TextView(this)
        title.text = getString(R.string.add_reminder)
        title.setPadding(20, 30, 20, 30)
        title.textSize = 18f
        title.typeface = ResourcesCompat.getFont(this, R.font.bold)

        title.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
        title.setTextColor(Color.WHITE)
        val checkedItem = 0
        MaterialAlertDialogBuilder(this, R.style.AlertDialogCustom)
            .setCustomTitle(title)
            .setSingleChoiceItems(items, checkedItem) { dialog, which ->
                repeatType = items[which]
            }
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->
                tvReminderTask.text = repeatType

            }
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which ->
            }
            .show()

    }

    private fun handleTaskRing() {
        ringType = resources.getString(R.string.notification_ringtone)
        val items = arrayOf(
            resources.getString(R.string.notification_ringtone),
            resources.getString(R.string.alarm_tone),
            resources.getString(R.string.ringtone)
        )

        val title = TextView(this)
        title.text = getString(R.string.select_tone)
        title.setPadding(20, 30, 20, 30)
        title.textSize = 18f
        title.typeface = ResourcesCompat.getFont(this, R.font.bold)

        title.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
        title.setTextColor(Color.WHITE)
        val checkedItem = 0
        MaterialAlertDialogBuilder(this, R.style.AlertDialogCustom)
            .setCustomTitle(title)
            .setSingleChoiceItems(items, checkedItem) { dialog, which ->
                ringType = items[which]
            }
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->
                Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show()
                tvRingtoneTask.text = ringType

            }
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which ->
            }
            .show()

    }

    private fun setTime() {
        val currentHour = calendarTime!!.get(Calendar.HOUR_OF_DAY);
        val currentMinute = calendarTime!!.get(Calendar.MINUTE);
        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { timePicker, hourOfDay, minutes ->

                calendarTime!![Calendar.HOUR_OF_DAY] = hourOfDay
                calendarTime!![Calendar.MINUTE] = minutes
                calendarTime!![Calendar.SECOND] = 0
                startTime = calendarTime!!.time
                handleTime()
                //updateTimeText(calendar!!, tvTaskTime)


            }, currentHour, currentMinute, false
        )

        timePickerDialog.show()


    }


    private fun setDate() {

        val year: Int = calendarDate!!.get(Calendar.YEAR)
        val month: Int = calendarDate!!.get(Calendar.MONTH)
        val day: Int = calendarDate!!.get(Calendar.DAY_OF_MONTH)
        Log.e("system ", "setDateStart: day $day month: $month year: $year")

        val date = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendarDate!!.set(Calendar.YEAR, year)
                calendarDate!!.set(Calendar.MONTH, monthOfYear)
                calendarDate!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                startDate = calendarDate!!.time
                handleDate()
                Log.e(
                    "setDateStart",
                    "DatePickerDialog: day $dayOfMonth month: $monthOfYear year: $year"
                )

            }, year, month, day
        )
        date.datePicker.minDate = System.currentTimeMillis() - 1000

        date.show()

    }

    private fun isToday(): Boolean {
        val c = Calendar.getInstance()
        val year: Int = c.get(Calendar.YEAR)
        val month: Int = c.get(Calendar.MONTH)
        val day: Int = c.get(Calendar.DAY_OF_MONTH)
        val systemMonth = DateHelper.getFormatDate( date = c)
        val currentMonth = DateHelper.getFormatDate(date =  startDate!!)
        return (systemMonth == currentMonth)
    }

    private fun handleTime() {
        if (startDate == null || !isToday()) {
            updateTimeText(calendarTime!!, tvTaskTime)
            return
        }
        if (isToday()) {
            if (startTime!!.time >= System.currentTimeMillis()) {

                if (startTime!!.time >= System.currentTimeMillis() + Constants.NINE_MINUTE_MILLIS) {
                    updateTimeText(calendarTime!!, tvTaskTime)

                } else {

                    val increaseTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)
                    Toast.makeText(
                        this,
                        getString(R.string.at_least) + " ${DateHelper.getFormatTime(
                            "hh:mm",
                            increaseTime
                        )} ",
                        Toast.LENGTH_LONG
                    )
                        .show()

                    startTime = null
                    tvTaskTime.text = getString(R.string.choose_time)
                    return
                }

            } else {
                Toast.makeText(this, getString(R.string.invalid_time), Toast.LENGTH_LONG)
                    .show()
                startTime = null
                tvTaskTime.text = getString(R.string.choose_time)
                return
            }

            return
        }
    }


    private fun handleDate() {
        updateDateText(calendarDate!!, tvTaskDate)

        if (startTime != null) {
            if (isToday()) {
                if (startTime!!.time >= System.currentTimeMillis()) {

                    if (startTime!!.time < System.currentTimeMillis() + Constants.NINE_MINUTE_MILLIS) {

                        val increaseTime =
                            System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10)
                        Toast.makeText(
                            this,
                            getString(R.string.at_least) + " ${DateHelper.getFormatTime(
                                "hh:mm",
                                increaseTime
                            )} ",
                            Toast.LENGTH_LONG
                        )
                            .show()

                        startTime = null
                        tvTaskTime.text = getString(R.string.choose_time)
                        return
                    }

                } else {
                    Toast.makeText(this, getString(R.string.invalid_time), Toast.LENGTH_LONG)
                        .show()
                    startTime = null
                    tvTaskTime.text = getString(R.string.choose_time)
                    return
                }

                return
            }
        }
    }


}
