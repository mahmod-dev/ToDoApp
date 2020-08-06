package com.mahmoud.todoapp

import android.app.ActionBar
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.tiagoaguiar.recyclermasterjava.util.Helper.getContactList
import com.mahmoud.todoapp.adapter.ContactsAdapter
import com.mahmoud.todoapp.model.Contact
import com.mahmoud.todoapp.roomDB.AppDatabase
import com.mahmoud.todoapp.roomDB.DatabaseHelperImp
import com.mahmoud.todoapp.util.CustomAlertDialog
import com.mahmoud.todoapp.util.CustomAlertDialog.getDialogInstance
import com.mahmoud.todoapp.util.dbUtil.Status
import com.mahmoud.todoapp.util.dbUtil.ViewModelFactory
import com.mahmoud.todoapp.viewmodel.ContactViewModel
import com.mahmoud.todoapp.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.activity_contacts.*
import kotlinx.coroutines.*


class ContactsActivity : AppCompatActivity() {
    companion object {
        var contactSelectedList = ArrayList<Contact>()
    }
    private var actionMode: ActionMode? = null
    private var contactsAdapter: ContactsAdapter? = null
    val TAG = "ContactsActivity"
    var dialog: AlertDialog? = null
    private lateinit var job: Job
    private lateinit var viewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        initViewModel()
        title = "My Contacts"
        contactSelectedList = ArrayList()
        setupObserver()

    }

    private fun initRecycleView(list: List<Contact>) {

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
                        Log.e(TAG, "onActionItemClicked:trueee " )
                        finish()
                        mode.finish()
                        return true
                    }

                    return false
                }

                override fun onDestroyActionMode(mode: ActionMode) {
                    contactsAdapter?.selectedItems?.clear()
                    val contacts: List<Contact> = contactsAdapter!!.getContacts()
                    for (contact in contacts) {
                        if (contact.isSelected) contact.isSelected = false
                    }
                    contactsAdapter?.notifyDataSetChanged()
                    actionMode = null
                    Log.e(TAG, "onDestroyActionMode")
                }
            })

        contactsAdapter?.toggleSelection(position)
        val size: Int = contactsAdapter?.selectedItems!!.size()
        val contact = contactsAdapter?.getContacts()!!.get(position)
        if (contact.isSelected){
            contactSelectedList.add(contact)
        }else{
            contactSelectedList.remove(contact)
        }
        Log.e(TAG, "enableActionModeData: $contact")
        if (size == 0) {
            actionMode?.finish()
        } else {
            actionMode?.title = size.toString()
            actionMode?.invalidate()
            val bar: ActionBar? = actionBar

        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
            this, ViewModelFactory(
                DatabaseHelperImp(AppDatabase.getInstance(applicationContext)),application
            )

        ).get(ContactViewModel::class.java)
    }

    private fun setupObserver() {
        val dialog =  getDialogInstance()
        viewModel.getContacts().observe(this@ContactsActivity,

            Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        dialog.dismiss()

                        it.data?.let { users ->
                           initRecycleView(users)
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


            }
        )

    }


}
