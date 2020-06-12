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
import com.mahmoud.todoapp.model.Event
import kotlinx.android.synthetic.main.item_event.view.*
import java.util.*


class EventsAdapter(var data: ArrayList<Event>) :
    RecyclerView.Adapter<EventsAdapter.ViewHolder>() {
    var mListener: OnItemClickListener? = null
    val selectedItems = SparseBooleanArray()


    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int)
    }

    fun setOnClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }

    fun getEvents(): List<Event> {
        return data
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_event, viewGroup, false)
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
        fun bind(event: Event) {

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
        EventsAdapter adapter = new EventsAdapter(data);
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