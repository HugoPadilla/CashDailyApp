package com.wenitech.cashdaily.framework.features.caja.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.framework.commons.BaseViewHolder
import com.wenitech.cashdaily.domain.entities.CashTransactions
import com.wenitech.cashdaily.databinding.ItemMovimientoCajaBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat

class RecyclerViewGastoAdapter(
    private val listener: Listener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface Listener {
        fun onClickItemMovementBox(item: CashTransactions, position: Int)
    }

    private var data: List<CashTransactions> = listOf()

    fun setData(data: List<CashTransactions>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val viewBinding =
            ItemMovimientoCajaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return mViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is mViewHolder -> holder.bind(data[position], position)
        }
    }

    private inner class mViewHolder(val binding: ItemMovimientoCajaBinding) :
        BaseViewHolder<CashTransactions>(binding.root) {

        override fun bind(item: CashTransactions, position: Int) {

            val stringValor: String
            val stringDate: String
            val numberFormatMoney = NumberFormat.getCurrencyInstance()
            stringValor = numberFormatMoney.format(item.value)
            val dateFormat = SimpleDateFormat("dd MMMM, yyyy")
            val date = item.serverTimestamp!!.toDate()
            stringDate = dateFormat.format(date)

            binding.tvValor.text = stringValor
            binding.tvDate.text = stringDate
            binding.tvDescription.text = item.description
            if (item.isExpense) {
                binding.imageViewPerfil2.setImageResource(R.drawable.ic_trending_up)
            } else {
                binding.imageViewPerfil2.setImageResource(R.drawable.ic_trending_down)
            }

            // On Click Listener
            binding.root.setOnClickListener {
                listener.onClickItemMovementBox(item, position)
            }


        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}