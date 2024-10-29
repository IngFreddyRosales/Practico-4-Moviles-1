package com.example.agendacontactos.api

import retrofit2.Call
import com.example.agendacontactos.model.Contacts
import retrofit2.http.GET

interface ContactApiService  {
    @GET("personas")
    fun getContacts(): Call<Contacts>
}