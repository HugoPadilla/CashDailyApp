package com.wenitech.cashdaily.features.caja.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.wenitech.cashdaily.R
import com.wenitech.cashdaily.base.BaseViewHolder
import com.wenitech.cashdaily.data.model.MovimientoCaja
import com.wenitech.cashdaily.databinding.ItemMovimientoCajaBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat

class RecyclerViewGastoAdapter(private val listener: Listener) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface Listener {
        fun onClickItemMovementBox(item: MovimientoCaja, position: Int)
    }

    private var data: List<MovimientoCaja> = listOf()

    fun setData(data: List<MovimientoCaja>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val viewBinding = ItemMovimientoCajaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return mViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is mViewHolder -> holder.bind(data[position], position)
        }
    }

    private inner class mViewHolder(val binding: ItemMovimientoCajaBinding) : BaseViewHolder<MovimientoCaja>(binding.root) {

        override fun bind(item: MovimientoCaja, position: Int) {


            val stringValor: String
            val stringDate: String
            val numberFormatMoney = NumberFormat.getCurrencyInstance()
            stringValor = numberFormatMoney.format(item.valor)
            val dateFormat = SimpleDateFormat("dd MMMM, yyyy")
            val date = item.fecha!!.toDate()
            stringDate = dateFormat.format(date)

            binding.tvValor.text = stringValor
            binding.tvDate.text = stringDate
            binding.tvDescription.text = item.descripcion
            if (item.agregarDinero!!) {
                binding.imageViewPerfil2.setImageResource(R.drawable.ic_asset_trending_up_verde)
            } else {
                binding.imageViewPerfil2.setImageResource(R.drawable.ic_asset_trending_down_rojo)
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