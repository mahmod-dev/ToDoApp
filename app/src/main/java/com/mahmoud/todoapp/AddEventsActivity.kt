package com.mahmoud.todoapp

import android.animation.ObjectAnimator
import android.animation.StateListAnimator
import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import co.tiagoaguiar.recyclermasterjava.util.Helper
import com.github.dhaval2404.imagepicker.ImagePicker
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.mahmoud.todoapp.util.ModalBottomSheet
import kotlinx.android.synthetic.main.activity_add_events.*
import java.io.File
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class AddEventsActivity : AppCompatActivity() {
    private var calendar: Calendar? = null
    private var modalDismissWithAnimation = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_add_events)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        actionBar?.title = "add events"
        actionBar?.setDisplayHomeAsUpEnabled(true)
        calendar = Calendar.getInstance()

        showModalBottomSheet()
     //   val title = etTaskBar.text.toString()
      //  val details = etTaskDetails.text.toString()

        tvStartTime.setOnClickListener {
            setTimeStart()

        }

        tvEndTime.setOnClickListener {
            setTimeEnd()
        }

        tvStartDate.setOnClickListener {
            setDateStart(tvStartDate)
        }

        tvEndDate.setOnClickListener {
            setDateEnd()
        }

        imgAddEvent.setOnClickListener {
            addImage(this)
        }

        tvAddPersons.setOnClickListener {
            requestPermissionAndOpenContactsActivity()
        }

    }


    private fun setTimeStart() {
        val currentHour = calendar!!.get(Calendar.HOUR_OF_DAY);
        val currentMinute = calendar!!.get(Calendar.MINUTE);
        val timePickerDialog = TimePickerDialog(
            this,
            OnTimeSetListener { timePicker, hourOfDay, minutes ->

                calendar!![Calendar.HOUR_OF_DAY] = hourOfDay
                calendar!![Calendar.MINUTE] = minutes
                calendar!![Calendar.SECOND] = 0

                updateTimeText(calendar!!, tvStartTime)
            }, currentHour, currentMinute, true
        )

        timePickerDialog.show()


    }

    private fun setTimeEnd() {
        val currentHour = calendar!!.get(Calendar.HOUR_OF_DAY);
        val currentMinute = calendar!!.get(Calendar.MINUTE);
        val timePickerDialog = TimePickerDialog(
            this,
            OnTimeSetListener { timePicker, hourOfDay, minutes ->

                calendar!![Calendar.HOUR_OF_DAY] = hourOfDay
                calendar!![Calendar.MINUTE] = minutes
                calendar!![Calendar.SECOND] = 0

                updateTimeText(calendar!!, tvEndTime)
            }, currentHour, currentMinute, true
        )

        timePickerDialog.show()


    }


    private fun setDateStart(textView: TextView) {

        val year: Int = calendar!!.get(Calendar.YEAR)
        val month: Int = calendar!!.get(Calendar.MONTH)
        val day: Int = calendar!!.get(Calendar.DAY_OF_MONTH)
        Log.e("system ", "setDateStart: day $day month: $month year: $year")

        val date = DatePickerDialog(
            this,
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar!!.set(Calendar.YEAR, year)
                calendar!!.set(Calendar.MONTH, monthOfYear)
                calendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateText(calendar!!, textView)
                Log.e(
                    "setDateStart",
                    "DatePickerDialog: day $dayOfMonth month: $monthOfYear year: $year"
                )

            }, year, month, day
        )
        date.show()

    }

    private fun setDateEnd() {

        val year: Int = calendar!!.get(Calendar.YEAR)
        val month: Int = calendar!!.get(Calendar.MONTH)
        val day: Int = calendar!!.get(Calendar.DAY_OF_MONTH)

        val date = DatePickerDialog(
            this,
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar!!.set(Calendar.YEAR, year)
                calendar!!.set(Calendar.MONTH, monthOfYear)
                calendar!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateText(calendar!!, tvEndDate)
            }, year, month, day
        )
        date.show()

    }

    private fun updateTimeText(c: Calendar, textView: TextView) {
        val timeText = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.time)
        textView.text = timeText
    }

    private fun updateDateText(c: Calendar, textView: TextView) {
        val myFormat = "dd MMM" //In which you need put here

        val sdf = SimpleDateFormat(myFormat, Locale.US)


        textView.text = sdf.format(c.time)

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

            //You can get File object from intent
            val file:File = ImagePicker.getFile(data)!!

            //You can also get File Path from intent
            val filePath:String = ImagePicker.getFilePath(data)!!
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

                            val intent = Intent(this@AddEventsActivity,ContactsActivity::class.java)
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


}
