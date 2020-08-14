package com.mahmoud.todoapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.mahmoud.todoapp.R
import com.mahmoud.todoapp.adapter.EventTaskAdapter
import com.mahmoud.todoapp.adapter.EventsAdapter
import com.mahmoud.todoapp.model.Event
import com.mahmoud.todoapp.model.EventTask
import com.mahmoud.todoapp.roomDB.AppDatabase
import com.mahmoud.todoapp.roomDB.DatabaseHelperImp
import com.mahmoud.todoapp.util.CustomAlertDialog.getDialogInstance
import com.mahmoud.todoapp.util.dbUtil.Status
import com.mahmoud.todoapp.util.dbUtil.ViewModelFactory
import com.mahmoud.todoapp.viewmodel.EventTaskViewModel
import com.mahmoud.todoapp.viewmodel.EventViewModel
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    val TAG = "HomeFragment"
    private var eventTaskAdapter: EventTaskAdapter? = null
    private lateinit var viewModel: EventTaskViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        initEventViewModel()
        setupEventObserver()
        /* val sortedList = users.sortedWith(compareBy(
             {
                 it.event?.createdDate
             },
             {
                 it.task?.createdDate
             }
         ))*/



        return view
    }

    private fun initEventViewModel() {
        viewModel = ViewModelProviders.of(
            this, ViewModelFactory(
                DatabaseHelperImp(AppDatabase.getInstance(context!!)), activity!!.application
            )

        ).get(EventTaskViewModel::class.java)
    }

    private fun setupEventObserver() {
        val dialog = activity?.getDialogInstance()
        viewModel.getEventTask().observe(this,

            Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        dialog?.dismiss()
                        it.data?.let { users ->

                            dialog?.dismiss()
                            initRecycleView(users)
                            Log.e(TAG, "setupObserver: $users ")
                        }
                    }
                    Status.LOADING -> {
                        dialog?.show()
                    }
                    Status.ERROR -> {
                        //Handle Error
                        dialog?.dismiss()

                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        Log.e(TAG, "setupObserver: " + it.message)

                    }
                }
            }
        )

    }

    private fun initRecycleView(list: List<EventTask>) {

        rvHomeOthers?.apply {
            layoutManager = LinearLayoutManager(context)
            eventTaskAdapter = EventTaskAdapter(list)
            adapter = eventTaskAdapter
            isNestedScrollingEnabled = false
            setItemViewCacheSize(20);
        }
    }


}
