package com.mahmoud.todoapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahmoud.todoapp.adapter.ContactsAdapter
import com.mahmoud.todoapp.model.Contact
import com.mahmoud.todoapp.roomDB.AppDatabase
import com.mahmoud.todoapp.roomDB.DatabaseHelperImp
import com.mahmoud.todoapp.util.CustomAlertDialog.getDialogInstance
import com.mahmoud.todoapp.util.dbUtil.Status
import com.mahmoud.todoapp.util.dbUtil.ViewModelFactory
import com.mahmoud.todoapp.viewmodel.ContactViewModel
import kotlinx.android.synthetic.main.activity_contacts.*


class ContactsActivity : AppCompatActivity() {
    var count: Int = 0
    var mActionModeIsActive: Boolean = false
    companion object {
        var contactSelectedList = ArrayList<Contact>()
    }

    private var actionMode: ActionMode? = null
    private var contactsAdapter: ContactsAdapter? = null
    val TAG = "ContactsActivity"
    var dialog: AlertDialog? = null
    private lateinit var viewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        title = "My Contacts"
        initViewModel()

        setupObserver()
        handleSearchContact()
    }

    private fun initRecycleView(list: List<Contact>) {

        rvContact.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            contactsAdapter = ContactsAdapter(list)
            adapter = contactsAdapter
            setItemViewCacheSize(100)
        }

        contactsAdapter!!.setOnClickListener(object : ContactsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, data: List<Contact>) {

                enableActionMode()
                actionModeClick(position)
            }

            override fun onItemLongClick(position: Int, data: List<Contact>) {
                enableActionMode()
                actionModeClick(position)
            }


        })


    }


    private fun enableActionMode() {
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
                        Log.e(TAG, "onActionItemClicked:trueee ")
                        finish()
                        return true
                    }

                    return false
                }

                override fun onDestroyActionMode(mode: ActionMode) {
                    actionMode = null
                    Log.e(TAG, "onDestroyActionMode")
                    for (i in contactSelectedList.indices) {
                        contactSelectedList[i].isSelected = false
                    }
                    contactsAdapter?.notifyDataSetChanged()
                    contactSelectedList.clear()

                }


            }


            )


    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
            this, ViewModelFactory(
                DatabaseHelperImp(AppDatabase.getInstance(applicationContext)), application
            )

        ).get(ContactViewModel::class.java)
    }

    private fun setupObserver() {
        val dialog = getDialogInstance()
        viewModel.getContacts().observe(this@ContactsActivity,

            Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        dialog.dismiss()

                        it.data?.let { users ->
                            if (!contactSelectedList.isNullOrEmpty()) {
                                enableActionMode()
                                updateActionModeSize()
                                for (i in contactSelectedList.indices) {
                                    for (j in users.indices) {
                                        if (users.get(j).name == contactSelectedList.get(i).name) {
                                            users.get(j).isSelected =
                                                contactSelectedList.get(i).isSelected
                                            count++
                                        }
                                    }
                                }
                            }
                            initRecycleView(users)
                            Log.e(TAG, "contact list size: ${users.size}")
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

    private fun actionModeClick(position: Int) {
        contactsAdapter?.toggleSelection(position)
        val contact = contactsAdapter?.getContacts()!!.get(position)
        if (contact.isSelected) {
            contactSelectedList.add(contact)
            count++
        } else {
            count--
            contact.isSelected = true

            if (contactSelectedList.contains(contact)) {
                contactSelectedList.remove(contact)
                Log.e(TAG, "contains: $contact")
                contact.isSelected = false
            }

        }
        Log.e(TAG, "enableActionModeData: $contact")

        if (count == 0) {
            actionMode?.finish()
            mActionModeIsActive = false

        } else {

            actionMode?.title = count.toString()
            actionMode?.invalidate()
            mActionModeIsActive = true

        }
    }

    private fun updateActionModeSize() {
        actionMode?.title = contactSelectedList.size.toString()

    }


    @SuppressLint("ClickableViewAccessibility")
    private fun handleSearchContact() {
        etSearchContact.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.fetchContactName(etSearchContact.text.toString())
                true
            } else {
                false
            }
        }




        etSearchContact?.setOnTouchListener(OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (event.action == MotionEvent.ACTION_UP) {
                if(event.getX() <= (etSearchContact!!.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width()))
                {
                    viewModel.fetchContactName("")
                    etSearchContact!!.setText("")

                    return@OnTouchListener true
                }
            }
            false
        })
    }


//    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
//        if (mActionModeIsActive) {
//            if (event.keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP) {
//                // handle your back button code here
//                return true // consumes the back key event - ActionMode is not finished
//            }
//        }
//        Log.e(TAG, "dispatchKeyEvent: " )
//
//        return super.dispatchKeyEvent(event)
//    }

}
