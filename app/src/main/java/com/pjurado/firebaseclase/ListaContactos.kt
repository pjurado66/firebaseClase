package com.pjurado.firebaseclase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pjurado.firebaseclase.databinding.ActivityListaContactosBinding
import com.pjurado.recyclerviewobjeto.Contacto
import com.pjurado.recyclerviewobjeto.ContactosAdapter

class ListaContactos : AppCompatActivity() {
    var directorio: ArrayList<Contacto> = ArrayList()
    var idList: ArrayList<String> = ArrayList()
    private lateinit var binding: ActivityListaContactosBinding
    val db = Firebase.firestore
    lateinit var adapter: ContactosAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaContactosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        creaDatos()

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerContactos.layoutManager = layoutManager

        adapter = ContactosAdapter(directorio, this)
        binding.recyclerContactos.adapter = adapter
        tocaRecycler()
        binding.floatingActionButton.setOnClickListener {
            val contacto = Contacto("Pedro Jurado", "987123456", "pjurado@gmail.com", 0)
            db.collection("Contactos")
                .add(contacto)
                .addOnSuccessListener { ref ->
                    directorio.add(contacto)
                    idList.add(ref.id)
                    adapter.notifyDataSetChanged()
                }
                .addOnFailureListener{ e ->
                    Snackbar.make(binding.root, e.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }
        }
    }

    private fun tocaRecycler() {
        val simpleCallback: ItemTouchHelper.SimpleCallback =
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    db.collection("Contactos")
                        .document(idList[viewHolder.adapterPosition])
                        .delete()
                        .addOnSuccessListener {
                            directorio.removeAt(viewHolder.adapterPosition)
                            idList.removeAt(viewHolder.adapterPosition)
                            adapter.notifyItemRemoved(viewHolder.adapterPosition)
                            Snackbar.make(binding.root, "borrado", Snackbar.LENGTH_SHORT)
                                .show()
                        }
                        .addOnFailureListener{ e ->
                            Snackbar.make(binding.root, e.toString(), Snackbar.LENGTH_SHORT)
                                .show()
                        }

                }
            }
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerContactos)
    }

    private fun creaDatos() {
        db.collection("Contactos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val contacto = document.toObject(Contacto::class.java)
                    directorio.add(contacto)
                    idList.add(document.id)
                    Snackbar.make(binding.root, contacto.nombre + " se ha añadido", Snackbar.LENGTH_SHORT)
                        .show()
                    adapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { exception ->
                Snackbar.make(binding.root, exception.toString(), Snackbar.LENGTH_SHORT)
                    .show()
            }
/*
        directorio.add(Contacto("Pedro Jurado", "987123456", "pjurado@gmail.com", 0))
        directorio.add(Contacto("Pedro Jurado", "987123456", "pjurado@gmail.com", 0))
        directorio.add(Contacto("Pepe Pérez", "987121256", "ppe@gmail.com", 0))
        directorio.add(Contacto("Antonio Gómex", "934643456", "aoox@gmail.com", 0))
        directorio.add(Contacto("Antonio Gómex", "934643456", "aoox@gmail.com", 0))
        directorio.add(Contacto("Antonio Gómex", "934643456", "aoox@gmail.com", 0))
        directorio.add(Contacto("Antonio Gómex", "934643456", "aoox@gmail.com", 0))
        directorio.add(Contacto("Antonio Gómex", "934643456", "aoox@gmail.com", 0))
        directorio.add(Contacto("Antonio Gómex", "934643456", "aoox@gmail.com", 0))
        directorio.add(Contacto("Pedro Jurado", "987123456", "pjurado@gmail.com", 0))
        directorio.add(Contacto("Pedro Jurado", "987123456", "pjurado@gmail.com", 0))
        directorio.add(Contacto("Pedro Jurado", "987123456", "pjurado@gmail.com", 0))
        directorio.add(Contacto("Pedro Jurado", "987123456", "pjurado@gmail.com", 0))
*/

    }
}