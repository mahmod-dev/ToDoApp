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
import com.mahmoud.todoapp.R
import com.mahmoud.todoapp.adapter.EventsAdapter
import com.mahmoud.todoapp.adapter.TasksAdapter
import com.mahmoud.todoapp.model.Event
import kotlinx.android.synthetic.main.fragment_events.*

/**
 * A simple [Fragment] subclass.
 */
class EventsFragment : Fragment() {
    private var eventsAdapter: EventsAdapter? = null
    private var rvEvents: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_events, container, false)
        rvEvents = view.findViewById(R.id.rvEvents);
        val data = ArrayList<Event>()
        data.add(
            Event(
                "عمل هام", "تفاصيل العمل الهام", "12:18 Pm", "1:15 Pm", "11 June", "12 June"
                , 0.0, 0.0, "11 June", true
            )
        )
        initRecycleView(data)


        eventsAdapter?.setOnClickListener(object : EventsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {

                val intent = Intent(context, DetailsTasksActivity::class.java)
                startActivity(intent)

            }

            override fun onItemLongClick(position: Int) {
            }
        })
        return view
    }

    private fun initRecycleView(list: ArrayList<Event>) {

        rvEvents?.apply {
            layoutManager = LinearLayoutManager(context)
            eventsAdapter = EventsAdapter(list)
            adapter = eventsAdapter


        }
    }

}
