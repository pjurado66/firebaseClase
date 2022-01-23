package com.pjurado.firebaseclase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pjurado.firebaseclase.databinding.ActivityDetallePersonaBinding
import com.pjurado.recyclerviewobjeto.Contacto

class DetallePersona : AppCompatActivity() {
    private lateinit var binding: ActivityDetallePersonaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePersonaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val persona: Contacto = intent.extras?.get("persona") as Contacto
        val id = persona.id

        binding.etNombre.setText(persona.nombre)
        binding.etEmail.setText(persona.email)
        binding.etTelefono.setText(persona.telefono)

        val db = Firebase.firestore
        binding.btnGuardar.setOnClickListener {
            val ref = db.collection("Contactos").document(id!!)
            persona.nombre = binding.etNombre.text.toString()
            persona.email = binding.etEmail.text.toString()
            persona.telefono = binding.etTelefono.text.toString()
            ref.set(persona)
            //ref.update("nombre", binding.etNombre.toString())
            finish()
        }
    }
}