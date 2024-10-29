import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agendacontactos.R
import com.example.agendacontactos.databinding.ItemContactInformationBinding
import com.example.agendacontactos.model.Contact

class ContactAdapter(private var contacts: List<Contact>) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(binding: ItemContactInformationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val name = binding.itemName
        private val phoneNumber = binding.itemPhoneNumber
        private val profile_picture = binding.imageView

        fun bind(contact: Contact) {
            // Asignar el nombre del contacto
            name.text = contact.name

            // Verificar si la lista de teléfonos no está vacía
            if (contact.phones.isNullOrEmpty()) {
                phoneNumber.text = "No phone number"
            } else {
                // Mostrar el primer número de teléfono en la lista
                phoneNumber.text = contact.phones[0].number
            }
            Glide.with(itemView.context)
                .load(contact.profile_picture)
                .placeholder(R.drawable.baseline_person_24)
                .error(R.drawable.baseline_person_24)
                .into(profile_picture)


        }
    }

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
        holder.bind(contact)
    }

    fun updateData(newContactList: List<Contact>) {
        this.contacts = newContactList
        notifyDataSetChanged()
    }
}
