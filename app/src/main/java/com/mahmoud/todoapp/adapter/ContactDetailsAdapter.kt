package com.mahmoud.todoapp.adapter

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mahmoud.todoapp.R
import com.mahmoud.todoapp.model.Contact
import kotlinx.android.synthetic.main.item_contacts.view.*
import java.util.*


class ContactDetailsAdapter(var data: ArrayList<Contact>, var item:Int =R.layout.item_contacts_event) :
    RecyclerView.Adapter<ContactDetailsAdapter.ViewHolder>() {
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
            .inflate(item, viewGroup, false)
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
        fun bind(contact: Contact) {

            tvName.text = contact.name

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
        /*
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
