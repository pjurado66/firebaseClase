package com.pjurado.firebaseclase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pjurado.recyclerviewobjeto.Contacto

class Repo {
    val db = Firebase.firestore

    fun getContactos() : LiveData<MutableList<Contacto>>{
        val listaContactosLiveData = MutableLiveData<MutableList<Contacto>>()
        db.collection("Contactos")
            .get()
            .addOnSuccessListener { result ->
                val mutableListaContactos = mutableListOf<Contacto>()
                for (document in result) {

                    val contacto = document.toObject(Contacto::class.java)
                    contacto.id = document.id
                    mutableListaContactos.add(contacto)
                }
                listaContactosLiveData.value = mutableListaContactos
            }
        return listaContactosLiveData
    }
}