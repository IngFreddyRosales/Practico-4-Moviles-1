package com.example.agendacontactos.model

import android.os.Parcelable


typealias Contacts = ArrayList<Contact>


data class Contact (
    val id: Int,
    val name: String,
    val last_name: String,
    val company: String,
    val address: String,
    val city: String,
    val state: String,
    val profile_picture: String,
    val phones: List<PhoneNumber>,
    val emails: List<Email>
)