package co.tiagoaguiar.recyclermasterjava.util

import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.core.content.ContextCompat
import com.github.dhaval2404.imagepicker.ImagePicker
import com.mahmoud.todoapp.BuildConfig
import com.mahmoud.todoapp.R
import com.mahmoud.todoapp.model.Contact


object Helper {
    val TAG = "Helper"
    fun getContactList(context: Context): ArrayList<Contact>{
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
            arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(activity, R.style.AlertDialogCustom)
        builder.setTitle("Choose picture")
        builder.setItems(options) { dialog, item ->
            if (options[item] == "Take Photo") {
                ImagePicker.with(activity)
                    .cameraOnly()
                    .crop()
                    .start()
            } else if (options[item] == "Choose from Gallery") {

                ImagePicker.with(activity)
                    .galleryOnly()
                    .crop()
                    .start()
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }


}