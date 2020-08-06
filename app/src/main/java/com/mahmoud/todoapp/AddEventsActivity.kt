package com.mahmoud.todoapp

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProviders
import co.tiagoaguiar.recyclermasterjava.util.Helper
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.mahmoud.todoapp.roomDB.AppDatabase
import com.mahmoud.todoapp.roomDB.DatabaseHelperImp
import com.mahmoud.todoapp.util.DateHelper.updateDateText
import com.mahmoud.todoapp.util.DateHelper.updateTimeText
import com.mahmoud.todoapp.util.ModalBottomSheet
import com.mahmoud.todoapp.util.dbUtil.Status
import com.mahmoud.todoapp.util.dbUtil.ViewModelFactory
import com.mahmoud.todoapp.viewmodel.EventViewModel
import kotlinx.android.synthetic.main.activity_add_events.*
import java.io.File
import java.util.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.recyclermasterjava.util.Helper.setProgressDialog
import com.mahmoud.todoapp.adapter.ContactDetailsAdapter
import com.mahmoud.todoapp.adapter.ContactsAdapter
import com.mahmoud.todoapp.model.Contact
import com.mahmoud.todoapp.model.Event
import com.mahmoud.todoapp.util.Constants.MINUTE_MILLIS
import com.mahmoud.todoapp.util.Constants.NINE_MINUTE_MILLIS
import com.mahmoud.todoapp.util.CustomAlertDialog
import com.mahmoud.todoapp.util.CustomAlertDialog.getDialogInstance
import com.mahmoud.todoapp.util.DateHelper
import kotlinx.android.synthetic.main.activity_contacts.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


class AddEventsActivity : AppCompatActivity() {
    private val TAG = "AddEventsActivity"
    var checkedItems = booleanArrayOf(true, false, false, false, false, false)
    var sub: CharSequence? = null
    private lateinit var viewModel: EventViewModel
    private var calendarDate: Calendar? = null
    private var calendarTime: Calendar? = null
    private var modalDismissWithAnimation = false
    var isEnable = false
    var arrReminderType: HashSet<String> = HashSet()

    var ringType: String? = null
    private var startDate: Date? = null
    private var endDate: Date? = null

    private var startTime: Date? = null
    private var endTime: Date? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_events)
        Log.e(TAG, "onCreate: " )
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        actionBar?.title = "add events"
        calendarTime = Calendar.getInstance()
        calendarDate = Calendar.getInstance()
        initViewModel()
        isEnable = true
        arrReminderType.add(resources.getString(R.string.before_10_min))
        // setupObserver()
        tvStartTime.setOnClickListener {
            setTimeStart()

        }

        tvEndTime.setOnClickListener {
            setTimeEnd()
        }

        tvStartDate.setOnClickListener {
            setDateStart()
        }

        tvEndDate.setOnClickListener {
            setDateEnd()
        }

        imgAddEvent.setOnClickListener {
            addImage(this)
        }


        carAddE.setOnClickListener {
            addImage(this)
        }


        imgAddE.setOnClickListener {
            addImage(this)
        }

        tvAddPersons.setOnClickListener {
            requestPermissionAndOpenContactsActivity()
        }

        imgArrowBackEvent.setOnClickListener {
            finish()
        }


        containerReminder.setOnClickListener {
            eventRepetitionDialog()
        }

        containerRingtone.setOnClickListener {
            eventRingDialog()


        }

        fabBellEvent.setOnClickListener {
            handleBell()


        }

        btnEventSave.setOnClickListener {
            insertEvent()
        }
    }


    private fun setTimeStart() {
        val currentHour = calendarTime!!.get(Calendar.HOUR_OF_DAY);
        val currentMinute = calendarTime!!.get(Calendar.MINUTE);
        val timePickerDialog = TimePickerDialog(
            this,
            OnTimeSetListener { timePicker, hourOfDay, minutes ->

                calendarTime!![Calendar.HOUR_OF_DAY] = hourOfDay
                calendarTime!![Calendar.MINUTE] = minutes
                calendarTime!![Calendar.SECOND] = 0

                startTime = calendarTime!!.time
                compareStartTime()
            }, currentHour, currentMinute, false
        )
        timePickerDialog.show()


    }

    private fun setTimeEnd() {
        val currentHour = calendarTime!!.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendarTime!!.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(
            this,
            OnTimeSetListener { timePicker, hourOfDay, minutes ->

                calendarTime!![Calendar.HOUR_OF_DAY] = hourOfDay
                calendarTime!![Calendar.MINUTE] = minutes
                calendarTime!![Calendar.SECOND] = 0
                endTime = calendarTime!!.time
                compareEndTime()
            }, currentHour, currentMinute, false
        )

        timePickerDialog.show()


    }


    private fun setDateStart() {
        val year: Int = calendarDate!!.get(Calendar.YEAR)
        val month: Int = calendarDate!!.get(Calendar.MONTH)
        val day: Int = calendarDate!!.get(Calendar.DAY_OF_MONTH)
        Log.e("system ", "setDateStart: day $day month: $month year: $year")

        val date = DatePickerDialog(
            this,
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendarDate!!.set(Calendar.YEAR, year)
                calendarDate!!.set(Calendar.MONTH, monthOfYear)
                calendarDate!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                startDate = calendarDate!!.time
                compareStartDate()
                Log.e(
                    "setDateStart",
                    "DatePickerDialog: day $dayOfMonth month: $monthOfYear year: $year"
                )

            }, year, month, day
        )
        date.datePicker.minDate = System.currentTimeMillis() - 1000

        date.show()

    }

    private fun setDateEnd() {
        val year: Int = calendarDate!!.get(Calendar.YEAR)
        val month: Int = calendarDate!!.get(Calendar.MONTH)
        val day: Int = calendarDate!!.get(Calendar.DAY_OF_MONTH)

        val date = DatePickerDialog(
            this,
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendarDate!!.set(Calendar.YEAR, year)
                calendarDate!!.set(Calendar.MONTH, monthOfYear)
                calendarDate!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                endDate = calendarDate!!.time

                compareEndDate()
            }, year, month, day
        )
        date.datePicker.minDate = System.currentTimeMillis() - 1000

        date.show()

    }


    private fun makeLine() {
        val text = "show more details"
        val ss = SpannableString(text)
        val clickableSpan1 = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(this@AddEventsActivity, "One", Toast.LENGTH_SHORT).show()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.BLUE
                ds.isUnderlineText = false
            }
        }

        ss.setSpan(clickableSpan1, 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        textView.setText(ss)
        // textView.setMovementMethod(LinkMovementMethod.getInstance())

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
            imgAddEvent.setImageURI(fileUri)
            imgAddE.visibility = View.GONE
            carAddE.visibility = View.GONE
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

    private fun requestPermissionAndOpenContactsActivity() {

        Dexter.withContext(this)
            .withPermissions(
                android.Manifest.permission.READ_CONTACTS
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {

                            val intent =
                                Intent(this@AddEventsActivity, ContactsActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            })
            .withErrorListener {
            }
            .check()

    }

    private fun showModalBottomSheet() {
        val modalBottomSheet = ModalBottomSheet.newInstance(modalDismissWithAnimation)
        modalBottomSheet.show(supportFragmentManager, ModalBottomSheet.TAG)
    }


    private fun handleBell() {
        if (isEnable) {
            fabBellEvent.setImageResource(R.drawable.ic_bell_cancel)
            isEnable = false
            tvRingtoneEvent.text = getString(R.string.without_tone)
        } else {
            fabBellEvent.setImageResource(R.drawable.ic_bell)
            isEnable = true
            if (ringType.equals(getString(R.string.without_tone))) {
                tvRingtoneEvent.text = getString(R.string.notification_ringtone)
            } else
                tvRingtoneEvent.text = ringType

        }


    }


    private fun eventRepetitionDialog() {
        val items = arrayOf(
            resources.getString(R.string.before_10_min),
            resources.getString(R.string.before_20_min),
            resources.getString(R.string.before_1_hour),
            resources.getString(R.string.before_2_hour),
            resources.getString(R.string.before_1_day),
            resources.getString(R.string.before_1_weak)
        )
        val materialDialog = MaterialAlertDialogBuilder(this, R.style.AlertDialogCustom)

        val title = TextView(this)
        title.text = getString(R.string.select_repetition)
        title.setPadding(20, 30, 20, 30)
        title.textSize = 18f
        title.typeface = ResourcesCompat.getFont(this, R.font.bold)

        title.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
        title.setTextColor(Color.WHITE)

        materialDialog.setCustomTitle(title)
        materialDialog.setMultiChoiceItems(items, checkedItems) { dialog, position, checked ->
            checkedItems[position] = checked

            if (checked)
                arrReminderType.add(items[position])
            else
                arrReminderType.remove(items[position])


        }
        materialDialog.setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->
            Toast.makeText(this, arrReminderType.toString(), Toast.LENGTH_SHORT).show()
            handleReminderArray()


        }
        materialDialog.setNegativeButton(resources.getString(R.string.cancel)) { dialog, which ->
            Toast.makeText(this, "Clicked cancel", Toast.LENGTH_SHORT).show()
        }

        val dialog = materialDialog.show()

//        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
//            .setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
//
//        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
//            .setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
//
//        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
//            .setTextColor(ContextCompat.getColor(this, R.color.white))

    }

    private fun eventRingDialog() {
        ringType = resources.getString(R.string.notification_ringtone)
        val items = arrayOf(
            resources.getString(R.string.without_tone),
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
        val checkedItem = 1
        MaterialAlertDialogBuilder(this, R.style.AlertDialogCustom)
            .setCustomTitle(title)
            .setSingleChoiceItems(items, checkedItem) { dialog, which ->
                ringType = items[which]


            }
            .setPositiveButton(resources.getString(R.string.ok)) { dialog, which ->
                tvRingtoneEvent.text = ringType

                if (ringType.equals(getString(R.string.without_tone))) {
                    isEnable = true
                    handleBell()
                } else {
                    isEnable = false
                    handleBell()
                }

            }
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which ->
            }
            .show()

    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
            this, ViewModelFactory(
                DatabaseHelperImp(AppDatabase.getInstance(applicationContext)),application
            )

        ).get(EventViewModel::class.java)
    }

    private fun setupObserver() {
        val dialog =  getDialogInstance()

        viewModel.getEvents().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    dialog.dismiss()
                    it.data?.let { users ->
                        finish()
                    }
                }
                Status.LOADING -> {
                    dialog.show()
                }
                Status.ERROR -> {
                    //Handle Error
                    dialog.dismiss()
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    Log.e(TAG, "setupObserver: " + it.message)

                }
            }
        })
    }

    private fun compareStartDate() {
        if (endDate == null) {
            updateDateText(calendarDate!!, tvStartDate)
            return
        }

        if (endDate != null) {
            if (startDate?.compareTo(endDate)!! <= 0) {
                updateDateText(calendarDate!!, tvStartDate)
            } else {
                Toast.makeText(this, getString(R.string.invalid_date), Toast.LENGTH_LONG).show()
                tvStartDate.text = getString(R.string.start_date)
            }
        }

    }

    private fun compareEndDate() {
        if (startDate == null) {
            Toast.makeText(
                this,
                getString(R.string.select_start_date_before),
                Toast.LENGTH_LONG
            ).show()
            return
        }

        if (startTime != null && isToday()) {
            if (startTime!!.time <= System.currentTimeMillis()) {
                startTime = null
                tvStartTime.text = getString(R.string.start_time)
            }
        }
        if (startDate?.compareTo(endDate)!! <= 0) {
            updateDateText(calendarDate!!, tvEndDate)
        } else {
            Toast.makeText(this, getString(R.string.invalid_date), Toast.LENGTH_LONG).show()
        }


    }

    private fun compareStartTime() {

        if (startDate == null) {
            Toast.makeText(this, getString(R.string.select_start_date_before), Toast.LENGTH_LONG)
                .show()
            return
        }

        if (endDate == null) {
            Toast.makeText(this, getString(R.string.select_end_date_before), Toast.LENGTH_LONG)
                .show()
            return
        }
        if (isToday()) {
            //today
            if (startTime!!.time >= System.currentTimeMillis()) {

                if (startTime!!.time >= System.currentTimeMillis() + NINE_MINUTE_MILLIS) {
                    updateTimeText(calendarTime!!, tvStartTime)
                    calendarTime!!.add(Calendar.MINUTE, 15)
                    updateTimeText(calendarTime!!, tvEndTime)
                    calendarTime!!.add(Calendar.MINUTE, -15)

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
                    tvStartTime.text = getString(R.string.start_time)
                    return
                }

            } else {
                Toast.makeText(this, getString(R.string.invalid_time), Toast.LENGTH_LONG)
                    .show()
                startTime = null
                tvStartTime.text = getString(R.string.start_time)
                return
            }
            return
        }

        updateTimeText(calendarTime!!, tvStartTime)
        calendarTime!!.add(Calendar.MINUTE, 15)
        updateTimeText(calendarTime!!, tvEndTime)
        calendarTime!!.add(Calendar.MINUTE, -15)
    }


    private fun compareEndTime() {
        if (startDate == null) {
            Toast.makeText(this, getString(R.string.select_start_date_before), Toast.LENGTH_LONG)
                .show()
            return
        }

        if (endDate == null) {
            Toast.makeText(this, getString(R.string.select_end_date_before), Toast.LENGTH_LONG)
                .show()
            return
        }

        if (startTime == null) {
            Toast.makeText(this, getString(R.string.select_start_time_before), Toast.LENGTH_LONG)
                .show()
            return

        }
        if (isToday()) {
            //today
            if (DateHelper.isTimeAfterOrEqual(endTime, startTime)!!) {
                updateTimeText(calendarTime!!, tvEndTime)

            } else {
                Toast.makeText(this, getString(R.string.invalid_time), Toast.LENGTH_LONG).show()
                tvEndTime.text = getString(R.string.end_time)
                endTime = null
            }
            return
        }

        if (endTime!!.time >= (startTime!!.time + MINUTE_MILLIS * 15)) {
            updateTimeText(calendarTime!!, tvEndTime)
        } else {
            Toast.makeText(this, getString(R.string.invalid_time), Toast.LENGTH_LONG).show()
            tvEndTime.text = getString(R.string.end_time)
            endTime = null
        }


    }


    private fun isToday(): Boolean {
        return (startDate!!.compareTo(endDate) == 0)
    }

    private fun handleReminderArray() {
        val builder = StringBuilder()
        for (element in arrReminderType) {

            if (builder.length >= 15) {
                builder.append(" ...")
                break
            }
            builder.append(element)
            builder.append(", ")
        }

        if (builder.endsWith(", ")) {
            builder.deleteCharAt(builder.length - 2)
        }
        if (builder.isEmpty()) {
            checkedItems[0] = true
            arrReminderType.add(getString(R.string.before_10_min))
            builder.append(getString(R.string.before_10_min))
        }
        tvReminderEvent.text = builder.toString()
    }

    private fun initRecycleView(list: ArrayList<Contact>) {

        rvContacts.apply {
            layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL, false)
            val contactsAdapter = ContactDetailsAdapter(list)
            adapter = contactsAdapter
        }
    }
    private fun insertEvent() {
        val title = etTitleEvent.text.toString()
        val details = etDetailsEvent.text.toString()
        val startTime = tvStartTime.text.toString()
        val endTime = tvEndTime.text.toString()
        val startDate = tvStartDate.text.toString()
        val endDate = tvEndDate.text.toString()

        if (title.isEmpty()) {
            etTitleEvent.error = resources.getString(R.string.title_is_empty)
            return
        }
        if (startTime.equals(resources.getString(R.string.start_time))) {
            tvStartTime.error = resources.getString(R.string.choose_time)
            return
        }

        if (endTime.equals(resources.getString(R.string.end_time))) {
            tvEndTime.error = resources.getString(R.string.choose_time)
            return
        }

        if (startDate.equals(resources.getString(R.string.start_date))) {
            tvStartDate.error = resources.getString(R.string.choose_date)
            return
        }

        if (endDate.equals(resources.getString(R.string.end_date))) {
            tvEndDate.error = resources.getString(R.string.choose_date)
            return
        }


        val event = Event()
        event.title = title
        event.details = details
        event.timeStart = startTime
        event.timeEnd = endTime
        event.dateStart = startDate
        event.dateEnd = endDate
        event.reminderRepeat = tvReminderEvent.text.toString()
        event.ringtone = tvRingtoneEvent.text.toString()
        event.isEnabledTone = isEnable
        val list = ArrayList<Contact>()
        list.add(Contact("Mahmoud", "0597796100", false))
        list.add(Contact("Ahmad", "059999999", false))
        list.add(Contact("Sami", "0598x88888", false))
        list.add(Contact("Ali", "059777777", false))
        if (!ContactsActivity.contactSelectedList.isNullOrEmpty()){
            Log.e(TAG, "insertContactsEvent: ${ContactsActivity.contactSelectedList.toString()}" )
            event.contactList = ContactsActivity.contactSelectedList

        }
        viewModel.insertEvents(event)

        setupObserver()

    }

    override fun onStart() {
        super.onStart()
        Log.e(TAG, "onStart: " )
        initRecycleView(ContactsActivity.contactSelectedList)

    }

    override fun onRestart() {
        super.onRestart()
        Log.e(TAG, "onRestart: " )
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume: " )
    }


}
