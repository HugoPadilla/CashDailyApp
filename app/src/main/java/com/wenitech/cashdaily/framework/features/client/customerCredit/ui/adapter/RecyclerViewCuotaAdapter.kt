package com.wenitech.cashdaily.framework.features.client.customerCredit.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.wenitech.cashdaily.domain.entities.Quota
import com.wenitech.cashdaily.databinding.ItemCuotaBinding
import com.wenitech.cashdaily.framework.commons.BaseViewHolder
import java.text.*

class RecyclerViewCuotaAdapter(private val listener: RecyclerViewCuotaListener) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var listQuota = listOf<Quota>()

    fun setData(listQuota: List<Quota>){
        this.listQuota = listQuota
        notifyDataSetChanged()
    }

    interface RecyclerViewCuotaListener {
        fun listenerOfItem(item: Quota, position: Int, root: CardView)
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

    inner class mViewHolder(private val binding: ItemCuotaBinding) : BaseViewHolder<Quota>(binding.root) {

        override fun bind(item: Quota, position: Int) {

            binding.fechaQuote.text = SimpleDateFormat("yyyy-MM-dd").format(item.timestamp?.toDate())
            binding.valueQuote.text = java.text.NumberFormat.getCurrencyInstance().format(item.value)
            binding.authorQuote.text = item.author

            binding.root.setOnClickListener {
                listener.listenerOfItem(item, position, binding.root)
            }
        }
    }
}