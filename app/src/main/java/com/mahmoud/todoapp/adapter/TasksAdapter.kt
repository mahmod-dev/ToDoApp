package com.mahmoud.todoapp.adapter

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.MapView
import com.mahmoud.todoapp.R
import com.mahmoud.todoapp.model.Task
import kotlinx.android.synthetic.main.item_task.view.*
import java.util.*


class TasksAdapter(var data: ArrayList<Task>) :
    RecyclerView.Adapter<TasksAdapter.ViewHolder>() {
    var mListener: OnItemClickListener? = null
    val selectedItems = SparseBooleanArray()


    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int)
    }

    fun setOnClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }

    fun getContacts(): List<Task> {
        return data
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_task, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        i: Int
    ) {
        viewHolder.bind(data[i])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var cardTask: CardView = itemView.cardTask
        var imgTaskBell: ImageView = itemView.imgTaskBell
        var imgTask: ImageView = itemView.imgAddTask
        var imgTaskDelete: ImageView = itemView.imgTaskDelete
        var tvTaskTitle: TextView = itemView.tvTaskTitle
        var tvTaskCreatedDate: TextView = itemView.tvTaskCreatedDate
        var tvTaskDetails: TextView = itemView.tvTaskDetails
        var mapTask: MapView = itemView.mapTask
        var tvTaskTime: TextView = itemView.tvTaskTime
        var tvTaskDate: TextView = itemView.tvTaskDate
        fun bind(tak: Task) {

        }

        init {
            itemView.setOnClickListener {
                if (mListener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                            mListener!!.onItemClick(position)
                    }
                }
            }

            itemView.setOnLongClickListener {
                if (mListener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        mListener!!.onItemLongClick(position)
                    }
                }
                false

            }
        }
    }



 /*
    public void initRecycleView() {
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(manager);
        ContactsAdapter adapter = new ContactsAdapter(data);
        rv.setAdapter(adapter);

    }
*/
        /*    public void initRecycleView(ArrayList<MyObject> data) {
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(RecyclerView.VERTICAL);

        binding.rv.setLayoutManager(manager);
        binding.rv.addItemDecoration(new VerticalSpacingItemDecorator(25));
        adapter = new CustomRecycleAdapter(data);
        binding.rv.setAdapter(adapter);

    }*/

}