package com.mahmoud.todoapp.fragments

import android.content.Intent
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
import androidx.recyclerview.widget.RecyclerView
import com.mahmoud.todoapp.DetailsEventsActivity
import com.mahmoud.todoapp.DetailsTasksActivity
import com.mahmoud.todoapp.R
import com.mahmoud.todoapp.adapter.EventsAdapter
import com.mahmoud.todoapp.adapter.TasksAdapter
import com.mahmoud.todoapp.model.Event
import com.mahmoud.todoapp.roomDB.AppDatabase
import com.mahmoud.todoapp.roomDB.DatabaseHelperImp
import com.mahmoud.todoapp.util.CustomAlertDialog.getDialogInstance
import com.mahmoud.todoapp.util.dbUtil.Status
import com.mahmoud.todoapp.util.dbUtil.ViewModelFactory
import com.mahmoud.todoapp.viewmodel.EventViewModel
import kotlinx.android.synthetic.main.fragment_events.*

/**
 * A simple [Fragment] subclass.
 */
class EventsFragment : Fragment() {
    val TAG = "EventsFragment"
    private var eventsAdapter: EventsAdapter? = null
    private var rvEvents: RecyclerView? = null
    private lateinit var viewModel: EventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_events, container, false)
        rvEvents = view.findViewById(R.id.rvEvents);
        initViewModel()
        setupObserver()

        eventsAdapter?.setOnClickListener(object : EventsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {

                val intent = Intent(context, DetailsEventsActivity::class.java)
                startActivity(intent)

            }

            override fun onItemLongClick(position: Int) {
            }
        })
        return view
    }

    private fun initRecycleView(list: List<Event>) {

        rvEvents?.apply {
            layoutManager = LinearLayoutManager(context)
            eventsAdapter = EventsAdapter(list)
            adapter = eventsAdapter
            isNestedScrollingEnabled = false
            setItemViewCacheSize(20);
            isDrawingCacheEnabled = true;
            drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH;
        }
    }
    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
            this, ViewModelFactory(
                DatabaseHelperImp(AppDatabase.getInstance(context!!)), activity!!.application
            )

        ).get(EventViewModel::class.java)
    }


    private fun setupObserver() {
        val dialog =  activity?.getDialogInstance()
        viewModel.getEvents().observe(this,

            Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        dialog?.dismiss()
                        it.data?.let { users ->
                            dialog?.dismiss()
                            initRecycleView(users)
                            Log.e(TAG, "setupObserver: $users " )
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



}
