import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agendacontactos.R
import com.example.agendacontactos.databinding.ItemContactInformationBinding
import com.example.agendacontactos.model.Contact

class ContactAdapter(
    private var contacts: List<Contact>,
    private val listener: OnContactClickListener
) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            ItemContactInformationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.bind(contact, listener)
    }

    fun updateData(newContactList: List<Contact>) {
        this.contacts = newContactList
        notifyDataSetChanged()
    }

    class ContactViewHolder(binding: ItemContactInformationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val name = binding.itemName
        private val phoneNumber = binding.itemPhoneNumber
        private val profile_picture = binding.imageView

        fun bind(contact: Contact, listener: OnContactClickListener) {

            name.text = contact.name

            if (contact.phones.isNullOrEmpty()) {
                phoneNumber.text = "No phone number"
            } else {
                phoneNumber.text = contact.phones[0].number
            }
            Glide.with(itemView.context)
                .load(contact.profile_picture)
                .placeholder(R.drawable.baseline_person_24)
                .error(R.drawable.baseline_person_24)
                .into(profile_picture)

            itemView.setOnClickListener {
                listener.onContactClick(contact)
            }
        }
    }

    interface OnContactClickListener {
        fun onContactClick(contact: Contact)
    }
}
