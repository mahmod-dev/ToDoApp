package co.tiagoaguiar.recyclermasterjava.util

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.provider.ContactsContract
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mahmoud.todoapp.BuildConfig
import com.mahmoud.todoapp.R
import com.mahmoud.todoapp.model.Contact


object Helper {
    val TAG = "Helper"
    fun getContactList(context: Context): ArrayList<Contact> {
        val data = ArrayList<Contact>()
        try {
            val cr: ContentResolver = context.contentResolver
            val cur: Cursor? = cr.query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null
            )
            if ((cur?.count ?: 0) > 0) {
                while (cur != null && cur.moveToNext()) {
                    val id: String = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID)
                    )
                    val name: String = cur.getString(
                        cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME
                        )
                    )
                    if (cur.getInt(
                            cur.getColumnIndex(
                                ContactsContract.Contacts.HAS_PHONE_NUMBER
                            )
                        ) > 0
                    ) {
                        val pCur: Cursor? = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            arrayOf(id),
                            null
                        )

                        if (pCur != null)
                            while (pCur.moveToNext()) {
                                val phoneNo: String = pCur.getString(
                                    pCur.getColumnIndex(
                                        ContactsContract.CommonDataKinds.Phone.NUMBER
                                    )
                                )
                                Log.e(TAG, "Name: $name")
                                Log.e(TAG, "Phone Number: $phoneNo")
                                val contact = Contact()
                                contact.name = name
                                contact.number = phoneNo
                                contact.isSelected = false
                                data.add(contact)
                            }
                        pCur?.close()
                    }
                }
            }

            cur?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return data
    }


    fun permissionAlreadyGranted(context: Context, permission: String): Boolean {
        //   Manifest.permission.CAMERA
        val result: Int =
            ContextCompat.checkSelfPermission(context, permission)
        return result == PackageManager.PERMISSION_GRANTED
    }


    fun openSettingsDialog(activity: Activity) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("Required Permissions")
        builder.setMessage("This app require permission to use awesome feature. Grant them in app settings.")
        builder.setPositiveButton(
            "Take Me To SETTINGS"
        ) { dialog, which ->
            dialog.cancel()
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri: Uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
            intent.data = uri
            activity.startActivityForResult(intent, 101)
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, which -> dialog.cancel() }


        builder.show()
    }


    fun selectImageDialog(activity: Activity) {
        val options =
            arrayOf<CharSequence>(
                activity.resources.getString(R.string.take_photo),
                activity.getString(R.string.choose_gallery)
            )
        val builder = MaterialAlertDialogBuilder(activity, R.style.AlertDialogCustom)

        val title = TextView(activity)
        title.text = activity.getString(R.string.choose_pic)
        title.setPadding(30, 30, 30, 30)
        title.textSize = 18f
        title.typeface = ResourcesCompat.getFont(activity, R.font.bold)

        title.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorPrimary))
        title.setTextColor(Color.WHITE)

        builder.setCustomTitle(title)
        builder.setItems(options) { dialog, item ->
            if (options[item] == activity.resources.getString(R.string.take_photo)) {
                ImagePicker.with(activity)
                    .cameraOnly()
                    .crop()
                    .start()
            } else if (options[item] == activity.getString(R.string.choose_gallery)) {

                ImagePicker.with(activity)
                    .galleryOnly()
                    .crop()
                    .start()
            }
        }

        builder.setNegativeButton(activity.resources.getString(R.string.cancel)) { dialog, which ->
            dialog.dismiss()

        }
        builder.show()
    }

    fun Activity.setProgressDialog(title: String = getString(R.string.please_wait)): AlertDialog {
        val llPadding = 30
        val ll = LinearLayout(this)
        ll.orientation = LinearLayout.VERTICAL
        ll.setPadding(llPadding, llPadding, llPadding, llPadding)
        ll.gravity = Gravity.CENTER
        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam
        val progressBar = ProgressBar(this)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, llPadding, 0)
        progressBar.layoutParams = llParam
        llParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        val tvText = TextView(this)
        tvText.text = title
        tvText.setTextColor(ContextCompat.getColor(this, R.color.purple_dark_2))
        tvText.textSize = 15f
        tvText.layoutParams = llParam
        ll.addView(progressBar)
        ll.addView(tvText)
        val builder = MaterialAlertDialogBuilder(this, R.style.AlertDialogCustom)
        builder.setCancelable(false)
        builder.setView(ll)
        val dialog = builder.create()
        val window: Window? = dialog.window
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window!!.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window!!.attributes = layoutParams
        }

        return dialog
    }


}