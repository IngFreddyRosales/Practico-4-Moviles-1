package com.example.agendacontactos.repository

import android.widget.Toast
import com.example.agendacontactos.api.ContactApiService
import com.example.agendacontactos.model.Contacts
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ContactRepository {

    fun getContactList(
        onSuccess: (Contacts) -> Unit,
        onError: (Throwable) -> Unit
    ){
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ContactApiService::class.java)
        service.getContacts().enqueue(object : Callback<Contacts> {
            override fun onResponse(call: Call<Contacts>, response: Response<Contacts>) {
                if(response.isSuccessful){
                    val post = response.body()
                    println(post)
                    onSuccess(post!!)
                }else{
                    onError(Throwable("Error en la respuesta (contactRepository)"))
                    val error = response.message()
                    val errorCode = response.code()

                    println(error + " error")
                    println(errorCode.toString() + " error code")
                }
            }

            override fun onFailure(call: Call<Contacts>, t: Throwable) {
                onError(t)
            }
        })
    }




}