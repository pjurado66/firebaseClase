package com.pjurado.recyclerviewobjeto

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pjurado.firebaseclase.DetallePersona
import com.pjurado.firebaseclase.ListaContactos
import com.pjurado.firebaseclase.R
import com.pjurado.firebaseclase.databinding.ItemContactoBinding

class ContactosAdapter(
    val directorio: ArrayList<Contacto>,
    val mcontext: ListaContactos,
    val idList: ArrayList<String>
)
    : RecyclerView.Adapter<ContactosAdapter.ContactoViewHolder>() {

    class ContactoViewHolder(val binding: ItemContactoBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactoViewHolder {
        val binding = ItemContactoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactoViewHolder, position: Int) {
        holder.binding.tvTelefono.text = directorio[position].telefono
        holder.binding.tvNombre.text = directorio[position].nombre
        holder.binding.tvEmail.text = directorio[position].email
        holder.binding.imageView.setImageResource(R.drawable.persona)

        holder.binding.root.setOnClickListener {
            val intent = Intent(mcontext, DetallePersona::class.java)
            intent.putExtra("persona", directorio[position])
            intent.putExtra("id", idList[position])
            mcontext.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return directorio.size
    }

}

