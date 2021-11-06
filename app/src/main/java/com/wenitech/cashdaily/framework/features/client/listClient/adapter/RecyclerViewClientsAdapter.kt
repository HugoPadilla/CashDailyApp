package com.wenitech.cashdaily.framework.features.client.listClient.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.framework.commons.BaseViewHolder
import com.wenitech.cashdaily.data.entities.ClientModel
import com.wenitech.cashdaily.databinding.ItemClienteBinding

class RecyclerViewClientsAdapter(
        private val listener: MyRecyclerViewInterface,
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var listClientModel: List<ClientModel> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listClientModel: List<ClientModel>) {
        this.listClientModel = listClientModel
        notifyDataSetChanged()
    }

    interface MyRecyclerViewInterface {
        fun onItemClick(clientModel: ClientModel, position: Int, cardView: View)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding = ItemClienteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MyViewHolder -> holder.bind(listClientModel[position], position)
        }
    }

    override fun getItemCount(): Int {
        return listClientModel.size
    }

    private inner class MyViewHolder(private val binding: ItemClienteBinding) : BaseViewHolder<ClientModel>(binding.root) {
        @SuppressLint("SetTextI18n")
        override fun bind(item: ClientModel, position: Int) {
            binding.textViewNombreCliente.text = item.fullName
            binding.textViewDireccionCliente.text = "${item.city}, ${item.direction}"
            if (item.creditActive) {
                binding.imageViewIsCreditActive.setImageResource(R.drawable.bg_button_dialogo_verde_solido_radiun)
            } else {
                binding.imageViewIsCreditActive.setImageResource(R.drawable.bg_button_gris_solido_radius)
            }

            binding.itemCliente.setOnClickListener {
                listener.onItemClick(item, position, binding.itemCliente)
            }
        }
    }
}