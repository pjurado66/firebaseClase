package com.pjurado.firebaseclase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.pjurado.firebaseclase.databinding.ActivityListaContactosBinding
import com.pjurado.recyclerviewobjeto.Contacto
import com.pjurado.recyclerviewobjeto.ContactosAdapter

class ListaContactos : AppCompatActivity() {
    private lateinit var directorio: MutableList<Contacto>
    var idList: ArrayList<String> = ArrayList()
    private lateinit var binding: ActivityListaContactosBinding
    val db = Firebase.firestore
    lateinit var adapter: ContactosAdapter
    private val viewModelContactos: ContactosViewModel by lazy {
        ViewModelProvider(this).get(ContactosViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaContactosBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerContactos.layoutManager = layoutManager

        adapter = ContactosAdapter(this, idList)
        binding.recyclerContactos.adapter = adapter

        viewModelContactos.isLoading.observe(this, Observer {
            if (it){
                binding.progressBar.visibility = View.VISIBLE
            }
            else{
                binding.progressBar.visibility = View.INVISIBLE
            }
        })

        viewModelContactos.getContactos().observe(
            this, Observer {
                directorio = it
                adapter.setDirectorio(directorio)
                adapter.notifyDataSetChanged()
        })

        tocaRecycler()
        binding.floatingActionButton.setOnClickListener {
            val contacto = Contacto("Pedro Jurado", "987123456", "pjurado@gmail.com", 0)
            db.collection("Contactos")
                .add(contacto)
                .addOnSuccessListener { ref ->
                    contacto.id = ref.id
                    ref.set(contacto)
                    directorio.add(contacto)
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