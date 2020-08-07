package com.mahmoud.todoapp.adapter

import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.mahmoud.todoapp.R
import com.mahmoud.todoapp.model.Contact
import kotlinx.android.synthetic.main.item_contacts.view.*
import kotlinx.android.synthetic.main.item_contacts_event.view.*
import java.util.*


class ContactDetailsAdapter(var data: ArrayList<Contact>, var item:Int =R.layout.item_contacts_event) :
    RecyclerView.Adapter<ContactDetailsAdapter.ViewHolder>() {
    var mListener: OnItemClickListener? = null
    val selectedItems = SparseBooleanArray()


    interface OnItemClickListener {
        fun onCloseClick(position: Int)
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
        var tvName: Chip = itemView.inputChip
        fun bind(contact: Contact) {

            tvName.text = contact.name

        }

        init {


            itemView.setOnLongClickListener {
                if (mListener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        mListener!!.onItemLongClick(position)
                    }
                }
                true

            }

            itemView.inputChip.setOnCloseIconClickListener {
                Log.e("inputChip", " inputChip" )
                if (mListener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                            mListener!!.onCloseClick(position)
                    }
                }
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
