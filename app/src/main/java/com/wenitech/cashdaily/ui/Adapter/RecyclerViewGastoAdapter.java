package com.wenitech.cashdaily.ui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.wenitech.cashdaily.Data.model.MovimientoCaja;
import com.wenitech.cashdaily.R;
import com.wenitech.cashdaily.databinding.ItemMovimientoCajaBinding;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecyclerViewGastoAdapter extends FirestoreRecyclerAdapter<MovimientoCaja, RecyclerViewGastoAdapter.mViewHolder> {

    public RecyclerViewGastoAdapter(@NonNull FirestoreRecyclerOptions<MovimientoCaja> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final mViewHolder holder, int position, @NonNull MovimientoCaja model) {

        String stringValor;
        String stringDate;

        NumberFormat numberFormatMoney = NumberFormat.getCurrencyInstance();
        stringValor = numberFormatMoney.format(model.getValor());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        Date date = model.getFecha().toDate();
        stringDate = dateFormat.format(date);

        holder.binding.tvValor.setText(stringValor);
        holder.binding.tvDate.setText(stringDate);
        // On Click Listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMovimientoCajaBinding viewBinding = ItemMovimientoCajaBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new mViewHolder(viewBinding);
    }

    public class mViewHolder extends RecyclerView.ViewHolder{
        private ItemMovimientoCajaBinding binding;
        public mViewHolder(@NonNull ItemMovimientoCajaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
