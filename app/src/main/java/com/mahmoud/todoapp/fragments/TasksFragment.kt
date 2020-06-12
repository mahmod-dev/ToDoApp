package com.mahmoud.todoapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahmoud.todoapp.DetailsTasksActivity
import com.mahmoud.todoapp.MapsActivity

import com.mahmoud.todoapp.R
import com.mahmoud.todoapp.adapter.EventsAdapter
import com.mahmoud.todoapp.adapter.TasksAdapter
import com.mahmoud.todoapp.model.Event
import com.mahmoud.todoapp.model.Task

/**
 * A simple [Fragment] subclass.
 */
class TasksFragment : Fragment() {
    private var tasksAdapter: TasksAdapter? = null
    private var rvTasks: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_tasks, container, false)

        rvTasks = view.findViewById(R.id.rvTasks);

       val data = ArrayList<Task>()
        data.add(Task("عمل هام","تفاصيل العمل الهام","12:18 Pm","1:15 Pm"
            ,0.0,0.0,"11 June",true))
        initRecycleView(data)

        tasksAdapter?.setOnClickListener(object :TasksAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(context, DetailsTasksActivity::class.java)
                startActivity(intent)

            }

            override fun onItemLongClick(position: Int) {
            }
        })
        return view
    }
    private fun initRecycleView(list: ArrayList<Task>) {

        rvTasks?.apply {
            layoutManager = LinearLayoutManager(context)
            tasksAdapter = TasksAdapter(list)
            adapter = tasksAdapter


        }
    }
}
