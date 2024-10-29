package com.example.agendacontactos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.agendacontactos.model.Contacts
import com.example.agendacontactos.repository.ContactRepository

class ContactViewModel: ViewModel() {

    private val _contacts = MutableLiveData<Contacts>().apply {
        value = arrayListOf()
    }
    val contacts: LiveData<Contacts> = _contacts

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

}