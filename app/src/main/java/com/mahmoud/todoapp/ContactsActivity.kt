package com.mahmoud.todoapp

import android.app.ActionBar
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahmoud.todoapp.adapter.ContactsAdapter
import com.mahmoud.todoapp.model.Contact
import kotlinx.android.synthetic.main.activity_contacts.*


class ContactsActivity : AppCompatActivity(){
    private var actionMode: ActionMode? = null
    private var contactsAdapter: ContactsAdapter? = null
    val TAG = "ContactsActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        val list = ArrayList<Contact>()
        title = "My Contacts"


        list.add(Contact("Mahmoud","0597796100",false))
        list.add(Contact("Ahmad","059999999",false))
        list.add(Contact("Sami","059888888",false))
        list.add(Contact("Ali","059777777",false))
        initRecycleView(list)

/*
        val job = GlobalScope.launch(Dispatchers.IO) {
            Dispatchers.IO
            Helper.getContactList(applicationContext, list)
            withContext(Dispatchers.Main){

                Log.e(TAG, list.size.toString())

            }
        }
*/


    /*    runBlocking {
            Toast.makeText(this@ContactsActivity, "asdasdas", Toast.LENGTH_LONG).show()
            Log.e(TAG, list.size.toString())
        }*/


    }

    private fun initRecycleView(list: ArrayList<Contact>) {

        rvContact.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            contactsAdapter = ContactsAdapter(list)
            adapter = contactsAdapter
        }


        contactsAdapter!!.setOnClickListener(object : ContactsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                enableActionMode(position)
            }

            override fun onItemLongClick(position: Int) {
                enableActionMode(position)
            }
        })


    }


    private fun enableActionMode(position: Int) {
        if (actionMode == null) actionMode =
            startSupportActionMode(object : ActionMode.Callback {
                override fun onCreateActionMode(
                    mode: ActionMode,
                    menu: Menu
                ): Boolean {
                    mode.menuInflater.inflate(R.menu.menu_contact, menu)
                    return true
                }

                override fun onPrepareActionMode(
                    mode: ActionMode,
                    menu: Menu
                ): Boolean {
                    return false
                }

                override fun onActionItemClicked(
                    mode: ActionMode,
                    item: MenuItem
                ): Boolean {
                    Log.e(TAG, "onActionItemClicked")

                    if (item.itemId == R.id.menuCheck) {
                        mode.finish()
                        return true
                    }

                    return false
                }

                override fun onDestroyActionMode(mode: ActionMode) {
                    contactsAdapter?.selectedItems?.clear()
                    val contacts: List<Contact> = contactsAdapter!!.getContacts()
                    for (contact in contacts) {
                        if (contact.isSelected) contact.isSelected=false
                    }
                    contactsAdapter?.notifyDataSetChanged()
                    actionMode = null
                    Log.e(TAG, "onDestroyActionMode")
                }
            })
        contactsAdapter?.toggleSelection(position)
        val size: Int = contactsAdapter?.selectedItems!!.size()
        if (size == 0) {
            actionMode?.finish()
        } else {
            actionMode?.setTitle(size.toString() + "")
            actionMode?.invalidate()
            val bar: ActionBar? = actionBar
            bar?.setBackgroundDrawable(ColorDrawable(Color.BLACK))
            bar?.setTitle("sfsdfsdfsdf")
        }
    }

}
