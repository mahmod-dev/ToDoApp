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
import com.mahmoud.todoapp.util.FileHelper


class TestActivity : AppCompatActivity() {
    var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
          //  FileHelper.createDir(applicationContext)

      //  FileHelper.createDir(this,"sadasdasdad")
    }

}