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
import com.mahmoud.todoapp.DetailsTasksActivity
import com.mahmoud.todoapp.MapsActivity

import com.mahmoud.todoapp.R
import com.mahmoud.todoapp.adapter.EventsAdapter
import com.mahmoud.todoapp.adapter.TasksAdapter
import com.mahmoud.todoapp.model.Event
import com.mahmoud.todoapp.model.Task
import com.mahmoud.todoapp.roomDB.AppDatabase
import com.mahmoud.todoapp.roomDB.DatabaseHelperImp
import com.mahmoud.todoapp.util.CustomAlertDialog.getDialogInstance
import com.mahmoud.todoapp.util.dbUtil.Status
import com.mahmoud.todoapp.util.dbUtil.ViewModelFactory
import com.mahmoud.todoapp.viewmodel.EventViewModel
import com.mahmoud.todoapp.viewmodel.TaskViewModel

/**
 * A simple [Fragment] subclass.
 */
class TasksFragment : Fragment() {
    val TAG = "TasksFragment"
    private var tasksAdapter: TasksAdapter? = null
    private var rvTasks: RecyclerView? = null
    private lateinit var viewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)

        rvTasks = view.findViewById(R.id.rvTasks);
        initViewModel()
        setupObserver()
        tasksAdapter?.setOnClickListener(object : TasksAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(context, DetailsTasksActivity::class.java)
                startActivity(intent)

            }

            override fun onItemLongClick(position: Int) {
            }
        })
        return view
    }

    private fun initRecycleView(list: List<Task>) {

        rvTasks?.apply {
            layoutManager = LinearLayoutManager(context)
            tasksAdapter = TasksAdapter(list)
            adapter = tasksAdapter


        }
    }


    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
            this, ViewModelFactory(
                DatabaseHelperImp(AppDatabase.getInstance(context!!)), activity!!.application
            )

        ).get(TaskViewModel::class.java)
    }


    private fun setupObserver() {
        val dialog = activity?.getDialogInstance()
        viewModel.getTasks().observe(this,

            Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        dialog?.dismiss()
                        it.data?.let { users ->
                            initRecycleView(users)
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
