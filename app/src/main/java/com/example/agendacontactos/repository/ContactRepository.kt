package com.example.agendacontactos.repository

import android.content.Context
import android.widget.Toast
import com.example.agendacontactos.api.ContactApiService
import com.example.agendacontactos.model.Contact
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

//    fun getContactById(
//        context: Context,
//        id: Int,
//        onSuccess: (Contacts) -> Unit,
//        onError: (Throwable) -> Unit
//    ){
//        val retrofit = RetrofitRepository.getRetrofitInstance()
//        val service = retrofit.create(ContactApiService::class.java)
//
//        service.getContactById(id).enqueue(object : Callback<Contacts> {
//            override fun onResponse(call: Call<Contacts>, response: Response<Contacts>) {
//                if(response.isSuccessful){
//                    val post = response.body()
//                    println(post.toString() + " post")
//                    onSuccess(post!!)
//                }else{
//                    onError(Throwable("Error en la respuesta (contactRepository)"))
//                    val error = response.message()
//                    val errorCode = response.code()
//
//                    println(error + " error")
//                    println(errorCode.toString() + " error code")
//                }
//            }
//
//            override fun onFailure(call: Call<Contacts>, t: Throwable) {
//                onError(t)
//            }
//        })
//    }

    fun getContactById(
        context: Context,
        id: Int,
        onSuccess: (Contact) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ContactApiService::class.java)

        service.getContactById(id).enqueue(object : Callback<Contact> {
            override fun onResponse(call: Call<Contact>, response: Response<Contact>) {
                if (response.isSuccessful) {
                    response.body()?.let { contact ->
                        println("respuesta de la API: $contact")
                        onSuccess(contact)

                    } ?: run {
                        val post = response.body()
                        println(post.toString() + " post")
                        onError(Throwable("El contacto no fue encontrado"))
                        Toast.makeText(context, "El contacto no fue encontrado", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    onError(Throwable("Error en la respuesta del servidor"))
                    Toast.makeText(context, "Error en la respuesta del servidor: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Contact>, t: Throwable) {
                onError(t)
                Toast.makeText(context, "Error en la conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun saveContact(
        contact: Contact,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getRetrofitInstance()
        val service = retrofit.create(ContactApiService::class.java)

        service.saveContact(contact).enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful){
                    onSuccess()
                }else{
                    onError(Throwable("Error al guardar el contacto"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                onError(t)
            }
        })

    }


}