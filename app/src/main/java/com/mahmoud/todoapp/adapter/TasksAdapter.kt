package com.mahmoud.todoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.recyclermasterjava.util.Helper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.gms.maps.MapView
import com.mahmoud.todoapp.R
import com.mahmoud.todoapp.model.Task
import com.mahmoud.todoapp.util.DateHelper
import com.mahmoud.todoapp.util.TasksType
import kotlinx.android.synthetic.main.item_task.view.*


class TasksAdapter(var data: List<Task>) :
    RecyclerView.Adapter<TasksAdapter.ViewHolder>() {
    var mListener: OnItemClickListener? = null


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
        var imgTaskType: ImageView = itemView.imgTaskType
        var tvTaskTitle: TextView = itemView.tvTaskTitle
        var tvTaskCreatedDate: TextView = itemView.tvTaskCreatedDate
        var tvTaskDetails: TextView = itemView.tvTaskDetails
        var mapTask: MapView = itemView.mapTask
        var tvTaskTime: TextView = itemView.tvTaskTime
        var tvTaskDate: TextView = itemView.tvTaskDate

        fun bind(task: Task) {
            tvTaskTitle.text = task.title
            tvTaskDetails.text = task.details
            tvTaskTime.text = "${DateHelper.timeToString(task.time)} "

            tvTaskDate.text = "${DateHelper.dateToString(itemView.context, task.date)}  "
            tvTaskCreatedDate.text = DateHelper.getRelationTime(task.createdDate)

            if (task.isEnabledTone) {
                imgTaskBell.setImageResource(R.drawable.ic_bell)
            } else
                imgTaskBell.setImageResource(R.drawable.ic_bell_cancel)

            if (task.imagePath.isNotEmpty())
                Glide.with(itemView.context).load(Helper.convertPathToUri(task.imagePath))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_man)
                    .into(imgTask);


            when (task.tasksType) {
                TasksType.Friends -> {
                    imgTaskType.setImageResource(R.drawable.ic_tasks_friends)
                }
                TasksType.Home -> {
                    imgTaskType.setImageResource(R.drawable.ic_task_home)

                }
                TasksType.Work -> {
                    imgTaskType.setImageResource(R.drawable.ic_task_work)

                }
                TasksType.Daily -> {
                    imgTaskType.setImageResource(R.drawable.ic_task_everyday)

                }
                else -> imgTaskType.setImageResource(R.drawable.ic_task_everyday)

            }
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