package com.wenitech.cashdaily.framework.features.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wenitech.cashdaily.databinding.ItemRouteBinding
import com.wenitech.cashdaily.data.entities.RutaModel
import com.wenitech.cashdaily.framework.commons.BaseViewHolder

class RecyclerViewRoutesAdapter(
    private val listener: InterfaceRecyclerViewRoute
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var listRoutes: List<RutaModel> = listOf()

    interface InterfaceRecyclerViewRoute {
        fun onRouteClick(route: RutaModel, position: Int, view: View)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setRouteData(list: List<RutaModel>) {
        this.listRoutes = list
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding = ItemRouteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    private inner class MyViewHolder(private val binding: ItemRouteBinding) :
        BaseViewHolder<RutaModel>(binding.root) {
        override fun bind(item: RutaModel, position: Int) {
            binding.textViewName.text = item.name
            binding.textViewDate.text = item.authorId

        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MyViewHolder -> holder.bind(listRoutes[position], position)
        }
    }

    override fun getItemCount(): Int {
        return listRoutes.size
    }

}