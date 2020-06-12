package com.mahmoud.todoapp.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mahmoud.todoapp.R
import com.mahmoud.todoapp.model.Contact
import kotlinx.android.synthetic.main.item_contacts.view.*
import java.util.*


class ContactsAdapter(var data: ArrayList<Contact>) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    var mListener: OnItemClickListener? = null
    val selectedItems = SparseBooleanArray()


    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int)
    }

    fun setOnClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }

    fun getContacts(): List<Contact> {
        return data
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_contacts, viewGroup, false)
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
        var tvName: TextView = itemView.tvContactName
        var tvNumber: TextView = itemView.tvContactNumber
        var tvIcon: TextView = itemView.tvIcon
        fun bind(contact: Contact) {
            if (!contact.name.isNullOrEmpty()) {
                tvIcon.text = contact.name!![0].toString()

            }
            val color = Random()

            tvIcon.background = oval(
                Color.argb(255, color.nextInt(256), color.nextInt(256), color.nextInt(256)), tvIcon
            )
            tvName.text = contact.name
            tvNumber.text = contact.number
            if (contact.isSelected) {
                val gradientDrawable = GradientDrawable()
                gradientDrawable.shape = GradientDrawable.RECTANGLE
                gradientDrawable.cornerRadius = 0f
                gradientDrawable.setColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.colorPrimary_light
                    )
                )
                itemView.background = gradientDrawable
            } else {
                val gradientDrawable = GradientDrawable()
                gradientDrawable.shape = GradientDrawable.RECTANGLE
                gradientDrawable.cornerRadius = 0f
                gradientDrawable.setColor(Color.WHITE)
                itemView.background = gradientDrawable
            }
        }

        init {
            itemView.setOnClickListener {
                if (mListener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        if (selectedItems.size() > 0 && mListener != null)
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

    fun toggleSelection(position: Int) {
        if (selectedItems.get(position)) {
            selectedItems.delete(position)
            data.get(position).isSelected = false
        } else {
            selectedItems.put(position, true)
            data.get(position).isSelected = true
        }
        notifyItemChanged(position)
    }


    companion object {
        private fun oval(@ColorInt color: Int, view: View): ShapeDrawable {
            val shapeDrawable = ShapeDrawable(OvalShape())
            shapeDrawable.intrinsicHeight = view.height
            shapeDrawable.intrinsicWidth = view.width
            shapeDrawable.paint.color = color
            return shapeDrawable
        } /*

        rvContact.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            contactsAdapter = ContactsAdapter(list)
            adapter = contactsAdapter
        }


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

}