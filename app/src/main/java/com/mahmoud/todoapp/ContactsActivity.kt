package com.mahmoud.todoapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.recyclermasterjava.util.Helper
import com.mahmoud.todoapp.adapter.ContactsAdapter
import com.mahmoud.todoapp.model.Contact
import kotlinx.android.synthetic.main.activity_contacts.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class ContactsActivity : AppCompatActivity() {
    private var actionMode: ActionMode? = null
    val TAG = "ContactsActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        val list = ArrayList<Contact>()
      val job =  GlobalScope.async {Dispatchers.IO
            Helper.getContactList(applicationContext,list)
        }


        runBlocking {
            job.await()
            Toast.makeText(this@ContactsActivity,"asdasdas",Toast.LENGTH_LONG).show()
            Log.e(TAG,list.size.toString())
            initRecycleView(list)

        }


    }
    public fun initRecycleView(list: ArrayList<Contact>) {
        val manager =  LinearLayoutManager(getApplicationContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        rvContact.setLayoutManager(manager);
        val adapter =  ContactsAdapter(list);
        rvContact.setAdapter(adapter);


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
                    Log.e(TAG,"onActionItemClicked")

                    if (item.itemId == R.id.menuCheck) {
                        mode.finish()
                        return true
                    }

                    return false
                }

                override fun onDestroyActionMode(mode: ActionMode) {
                Log.e(TAG,"onDestroyActionMode")
                }
            })
       /* emailAdapter.toggleSelection(position)
        val size: Int = emailAdapter.selectedItems.size()
        if (size == 0) {
            actionMode?.finish()
        } else {
            actionMode?.setTitle(size.toString() + "")
            actionMode?.invalidate()
        }*/
    }

}
