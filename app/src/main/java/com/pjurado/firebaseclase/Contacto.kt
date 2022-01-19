package com.pjurado.recyclerviewobjeto

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Contacto(var nombre: String = "",
               var telefono: String = "",
               var email: String = "",
               var foto: Int = 0): Serializable  {


}