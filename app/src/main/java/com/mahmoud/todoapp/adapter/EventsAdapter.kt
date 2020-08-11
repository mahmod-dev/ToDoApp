package com.mahmoud.todoapp.adapter

import android.util.SparseBooleanArray
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
import com.mahmoud.todoapp.util.DateHelper
import kotlinx.android.synthetic.main.item_event.view.*


class EventsAdapter(var data: List<Event>) :
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

            tvEventTitle.text = event.title
            tvEventDetails.text = event.details
            tvEventTime.text =
                "${DateHelper.timeToString(event.timeStart)} - ${DateHelper.timeToString(event.timeEnd)} "

            tvEventDate.text =
                "${DateHelper.dateToString(itemView.context,event.dateStart)} - ${DateHelper.dateToString(itemView.context,event.dateEnd)} "

            if (event.isEnabledTone) {
                imgEventBell.setImageResource(R.drawable.ic_bell)
            } else
                imgEventBell.setImageResource(R.drawable.ic_bell_cancel)

            tvEventCreatedDate.text = DateHelper.getRelationTime(event.createdDate)

            if (event.imagePath.isNotEmpty()){
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