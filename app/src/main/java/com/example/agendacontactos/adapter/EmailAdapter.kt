package com.example.agendacontactos.adapter

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.agendacontactos.databinding.ItemEmailLayoutBinding
import com.example.agendacontactos.model.Email

class EmailAdapter(
    private var emailList: List<Email>
) : RecyclerView.Adapter<EmailAdapter.EmailViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmailAdapter.EmailViewHolder {
        return EmailViewHolder(
            ItemEmailLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return emailList.size
    }

    override fun onBindViewHolder(holder: EmailViewHolder, position: Int) {
        val email = emailList[position]
        holder.bind(email)
    }

    fun updateData(newEmailList: List<Email>) {
        this.emailList = newEmailList
        notifyDataSetChanged()
    }

    class EmailViewHolder (private val binding: ItemEmailLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val emailTextView = binding.itemEmail

        fun bind(email: Email) {
            emailTextView.text = Editable.Factory.getInstance().newEditable(email.email)
        }

    }
}
