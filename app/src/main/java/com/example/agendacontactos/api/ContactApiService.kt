package com.example.agendacontactos.api

import com.example.agendacontactos.model.Contact
import retrofit2.Call
import com.example.agendacontactos.model.Contacts
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ContactApiService {
    @GET("personas")
    fun getContacts(): Call<Contacts>

    @GET("personas/{id}")
    fun getContactById(@Path("id") id: Int): Call<Contact>

    @POST("personas")
    fun saveContact(@Body contact: Contact): Call<Void>

}