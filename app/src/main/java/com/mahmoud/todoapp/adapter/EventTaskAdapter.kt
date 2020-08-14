package com.mahmoud.todoapp.adapter

import android.util.Log
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
import com.mahmoud.todoapp.model.Event
import com.mahmoud.todoapp.model.EventTask
import com.mahmoud.todoapp.model.Task
import com.mahmoud.todoapp.util.DateHelper
import com.mahmoud.todoapp.util.TasksType
import kotlinx.android.synthetic.main.item_event.view.*
import kotlinx.android.synthetic.main.item_event.view.cardEvent
import kotlinx.android.synthetic.main.item_task.view.*

class EventTaskAdapter(var data: List<EventTask>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var mListener: OnItemClickListener? = null


    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int)
    }

    fun setOnClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }

    fun getEvents(): List<EventTask> {
        return data
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {
        val view: View?
        Log.e("TAG", "onCreateViewHolder: ")

        if (i == 0) {
            view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_event, viewGroup, false)
            return ViewHolderEvent(view)
        } else {
            view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_task, viewGroup, false)
            return ViewHolderTask(view)
        }

    }

    override fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        i: Int
    ) {
        if (viewHolder.itemViewType == 0) {
            val eventView = viewHolder as ViewHolderEvent
            eventView.bindEvent(data[i].event!!)
        } else {
            val taskView = viewHolder as ViewHolderTask
            taskView.bindTask(data[i].task!!)
        }


    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        if (data[position].type == 0) {
            return 0
        } else
            return 1
    }


    inner class ViewHolderEvent(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindEvent(event: Event) {
            var cardEvent: CardView = itemView.cardEvent
            var imgEventBell: ImageView = itemView.imgEventBell
            var imgEvent: ImageView = itemView.imgEvent
            var imgEventDelete: ImageView = itemView.imgEventDelete
            var tvEventTitle: TextView = itemView.tvEventTitle
            var tvEventCreatedDate: TextView = itemView.tvEventCreatedDate
            var tvEventDetails: TextView = itemView.tvEventDetails
            var mapEvent: MapView = itemView.mapEvent
            var tvEventTime: TextView = itemView.tvEventTime
            var tvEventDate: TextView = itemView.tvEventDate


            tvEventTitle.text = event.title
            tvEventDetails.text = event.details
            tvEventTime.text =
                "${DateHelper.timeToString(event.timeStart)} - ${DateHelper.timeToString(event.timeEnd)} "

            tvEventDate.text =
                "${DateHelper.dateToString(
                    itemView.context,
                    event.dateStart
                )} - ${DateHelper.dateToString(itemView.context, event.dateEnd)} "

            if (event.isEnabledTone) {
                imgEventBell.setImageResource(R.drawable.ic_bell)
            } else
                imgEventBell.setImageResource(R.drawable.ic_bell_cancel)

            tvEventCreatedDate.text = DateHelper.getRelationTime(event.createdDate)

            if (event.imagePath.isNotEmpty()) {
                Glide.with(itemView.context).load(Helper.convertPathToUri(event.imagePath))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_man)
                    .into(imgEvent);
            }
            // imgEvent.setImageBitmap(Helper.convertPathToBitmap(event.imagePath))

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

    inner class ViewHolderTask(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindTask(task: Task) {
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


}