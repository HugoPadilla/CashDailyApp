package com.wenitech.cashdaily.features.client.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.data.model.Cliente
import com.wenitech.cashdaily.databinding.ItemClienteBinding

class RecyclerViewClienteAdapter(
        options: FirestoreRecyclerOptions<Cliente?>, var listener: ReciclerViewClienteInteface,
) : FirestoreRecyclerAdapter<Cliente, RecyclerViewClienteAdapter.MyViewHolder>(options) {

    interface ReciclerViewClienteInteface {
        fun onClientClicked(cardView: View?, cliente: Cliente?)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: Cliente) {
        holder.binding.textViewNombreCliente.text = model.fullName
        holder.binding.textViewInicialCliente.text = "A"
        holder.binding.textViewDireccionCliente.text = model.city + ", " + model.direction
        holder.binding.itemCliente.setOnClickListener { listener.onClientClicked(holder.binding.itemCliente, model) }
        if (model.isCreditActive) {
            holder.binding.imageViewIsCreditActive.setImageResource(R.drawable.bg_button_dialogo_verde_solido_radiun)
        } else {
            holder.binding.imageViewIsCreditActive.setImageResource(R.drawable.bg_button_gris_solido_radius)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewBinding = ItemClienteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(viewBinding)
    }

    class MyViewHolder(var binding: ItemClienteBinding) : RecyclerView.ViewHolder(binding.root)
}