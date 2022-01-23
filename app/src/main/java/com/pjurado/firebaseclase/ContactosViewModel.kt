package com.pjurado.firebaseclase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pjurado.recyclerviewobjeto.Contacto
import kotlinx.coroutines.launch

class ContactosViewModel: ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    fun getContactos(): LiveData<MutableList<Contacto>>{
        val listaContactos = MutableLiveData<MutableList<Contacto>>()

        viewModelScope.launch{
            isLoading.postValue(true)
            Repo().getContactos().observeForever {
                listaContactos.value = it
                isLoading.postValue(false)
            }
        }
        return listaContactos
    }
}