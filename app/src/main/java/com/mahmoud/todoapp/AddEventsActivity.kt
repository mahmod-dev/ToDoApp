package com.mahmoud.todoapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Selection
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_add_events.*


class AddEventsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_events)
        getWindow().statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary);


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
//        textView.setMovementMethod(LinkMovementMethod.getInstance())

        tvAddPersons.setOnClickListener {
            startActivity(Intent(this@AddEventsActivity, ContactsActivity::class.java))
        }
    }
}
