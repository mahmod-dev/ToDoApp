package co.tiagoaguiar.recyclermasterjava.util

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.location.LocationManager
import android.net.Uri
import android.provider.ContactsContract
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mahmoud.todoapp.BuildConfig
import com.mahmoud.todoapp.model.Contact


object Helper {
    val TAG = "Helper"
    fun getContactList(context: Context, data: ArrayList<Contact>) /*= GlobalScope().launch*/ {
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
                                data.add(Contact())
                            }
                        pCur?.close()
                    }
                }
            }

            cur?.close()
        } catch (e: Exception) {
            /* withContext(Dispatchers.Main){
                 Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
             }*/
        }
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
        builder.setPositiveButton("Take Me To SETTINGS"
        ) { dialog, which ->
            dialog.cancel()
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri: Uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
            intent.data = uri
            activity.startActivityForResult(intent, 101)
        }
        builder.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.show()
    }









}