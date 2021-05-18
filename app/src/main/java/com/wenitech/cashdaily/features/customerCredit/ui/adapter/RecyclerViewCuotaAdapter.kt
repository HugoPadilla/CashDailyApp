package com.wenitech.cashdaily.features.customerCredit.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.data.model.Cuota
import com.wenitech.cashdaily.databinding.ItemCuotaBinding
import com.wenitech.cashdaily.base.BaseViewHolder
import java.text.*

class RecyclerViewCuotaAdapter(private val listener: RecyclerViewCuotaListener) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var listQuota = listOf<Cuota>()

    fun setData(listQuota: List<Cuota>){
        this.listQuota = listQuota
        notifyDataSetChanged()
    }

    interface RecyclerViewCuotaListener {
        fun listenerOfItem(item: Cuota, position: Int, root: CardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {
        val binding = ItemCuotaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return mViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is mViewHolder ->{
                holder.bind(listQuota[position],position)
            }
        }
    }

    override fun getItemCount(): Int {
        return listQuota.size
    }

    inner class mViewHolder(private val binding: ItemCuotaBinding) : BaseViewHolder<Cuota>(binding.root) {

        override fun bind(item: Cuota, position: Int) {

            binding.fechaQuote.text = SimpleDateFormat("yyyy-MM-dd").format(item.fechaCreacion?.toDate())
            binding.valueQuote.text = java.text.NumberFormat.getCurrencyInstance().format(item.valorCuota)
            binding.authorQuote.text = item.nombreCreacion

            if (item.estadoEditado!!.isEmpty() || item.estadoEditado == "Normal") {
                // Todo: estado normal
                binding.imageViewItemCuotaTrading.setImageResource(R.drawable.ic_asset_trending_up_verde)
            } else if (item.estadoEditado == "Editado") {
                // Todo: editado
                binding.imageViewItemCuotaTrading.setImageResource(R.drawable.ic_asset_trending_down_azul)
            } else if (item.estadoEditado == "Eliminado") {
                binding.imageViewItemCuotaTrading.setImageResource(R.drawable.ic_asset_trending_down_rojo)
            }

            binding.root.setOnClickListener {
                listener.listenerOfItem(item, position, binding.root)
            }
        }
    }
}