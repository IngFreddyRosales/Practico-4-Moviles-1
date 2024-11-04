package com.example.agendacontactos.adapter

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.agendacontactos.databinding.ItemPhoneLayoutBinding
import com.example.agendacontactos.model.PhoneNumber

class PhoneAdapter(
    private var phoneList: List<PhoneNumber>,
) : RecyclerView.Adapter<PhoneAdapter.PhoneViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder {
        return PhoneViewHolder(
            ItemPhoneLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return phoneList.size
    }

    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
        val phoneNumber = phoneList[position]
        holder.bind(phoneNumber)
    }

    fun updateData(newPhoneList: List<PhoneNumber>) {
        this.phoneList = newPhoneList
        notifyDataSetChanged()
    }

    class PhoneViewHolder(private val binding: ItemPhoneLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val phoneTextView = binding.itemPhone

        fun bind(phoneNumber: PhoneNumber) {
            phoneTextView.text = Editable.Factory.getInstance().newEditable(phoneNumber.number)

            binding.btnDeletePhone.setOnClickListener {


            }
        }
    }

}
