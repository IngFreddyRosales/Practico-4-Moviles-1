package com.example.agendacontactos.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.agendacontactos.model.Contact
import com.example.agendacontactos.model.Contacts
import com.example.agendacontactos.repository.ContactRepository

class ContactViewModel: ViewModel() {

    private val _contacts = MutableLiveData<Contacts>().apply {
        value = arrayListOf()
    }
    val contacts: LiveData<Contacts?> = _contacts

    private val _contact = MutableLiveData<Contact?>()
    val contact: LiveData<Contact?> = _contact

    fun loadContacts(){
        ContactRepository.getContactList(
            onSuccess = {
                println("Datos recibidos de la API: $it")
                _contacts.value = it
            }, onError = {
                println("Error en la respuesta (contactViewModel)")
                it.printStackTrace()
            }
        )
    }

    fun loadContactById(context: Context,id: Int){
        ContactRepository.getContactById(
            context,
            id,
            onSuccess = { contact ->
                _contact.value = contact
            },
            onError = { error ->
                error.printStackTrace()
                _contact.value = null
            }
        )
    }

    fun saveContact(contact: Contact) {
        ContactRepository.saveContact(
            contact,
            onSuccess = {
                println("Contacto guardado")
            },
            onError = {
                println("Error al guardar el contacto")
                it.printStackTrace()
            }

        )
    }
}