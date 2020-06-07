package co.tiagoaguiar.recyclermasterjava.util

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import com.mahmoud.todoapp.model.Contact
import kotlinx.coroutines.*


object Helper {
    val TAG = "Helper"
      fun getContactList(context: Context,data: ArrayList<Contact>) /*= GlobalScope().launch*/ {
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
        }catch (e:Exception){
           /* withContext(Dispatchers.Main){
                Toast.makeText(context,e.message,Toast.LENGTH_LONG).show()
            }*/
        }
    }
}