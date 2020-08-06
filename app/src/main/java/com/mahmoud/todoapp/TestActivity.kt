package com.mahmoud.todoapp

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.mahmoud.todoapp.util.CustomAlertDialog
import com.mahmoud.todoapp.util.CustomAlertDialog.getDialogInstance


class TestActivity : AppCompatActivity() {
    var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        if (!(this as Activity).isFinishing) {

            val dialog =  getDialogInstance()
            dialog?.show()
        }
//        val llPadding = 30
//        val ll = LinearLayout(this)
//        ll.orientation = LinearLayout.VERTICAL
//        ll.setPadding(llPadding, llPadding, llPadding, llPadding)
//        ll.gravity = Gravity.CENTER
//        var llParam = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.WRAP_CONTENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT
//        )
//        llParam.gravity = Gravity.CENTER
//        ll.layoutParams = llParam
//        val progressBar = ProgressBar(this)
//        progressBar.isIndeterminate = true
//        progressBar.setPadding(0, 0, llPadding, 0)
//        progressBar.layoutParams = llParam
//        llParam = LinearLayout.LayoutParams(
//            ViewGroup.LayoutParams.WRAP_CONTENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//        llParam.gravity = Gravity.CENTER
//        val tvText = TextView(this)
//        tvText.text = title
//        tvText.setTextColor(ContextCompat.getColor(this, R.color.purple_dark_2))
//        tvText.textSize = 15f
//        tvText.layoutParams = llParam
//        ll.addView(progressBar)
//        ll.addView(tvText)
//        val builder = MaterialAlertDialogBuilder(this, R.style.AlertDialogCustom)
//        builder.setCancelable(true)
//        builder.setView(ll)
//        val dialog = builder.create()
//        val window: Window? = dialog?.window
//        if (window != null) {
//            val layoutParams = WindowManager.LayoutParams()
//            layoutParams.copyFrom(dialog?.window!!.attributes)
//            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
//            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
//            dialog?.window!!.attributes = layoutParams
//
//            dialog.show()
//
//
//        }


    }

}