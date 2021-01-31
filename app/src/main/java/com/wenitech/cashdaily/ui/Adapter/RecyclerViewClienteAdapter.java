package com.wenitech.cashdaily.ui.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.wenitech.cashdaily.databinding.ItemClienteBinding;
import com.wenitech.cashdaily.Data.model.Cliente;

public class RecyclerViewClienteAdapter extends FirestoreRecyclerAdapter<Cliente, RecyclerViewClienteAdapter.MyViewHolder> {

    ReciclerViewClienteInteface listener;

    public RecyclerViewClienteAdapter(@NonNull FirestoreRecyclerOptions<Cliente> options, ReciclerViewClienteInteface listener) {
        super(options);
        this.listener = listener;
    }

    public interface ReciclerViewClienteInteface {
        void onClientClicked(View cardView, Cliente cliente);
    }

    @Override
    protected void onBindViewHolder(@NonNull final MyViewHolder holder, final int position, @NonNull final Cliente model) {
        holder.binding.textViewNombreCliente.setText(model.getFullName());
        holder.binding.textViewInicialCliente.setText("A");
        holder.binding.textViewDireccionCliente.setText(model.getCity() + ", " + model.getDirection());
        holder.binding.itemCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClientClicked(holder.binding.itemCliente, model);
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemClienteBinding viewBinding = ItemClienteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(viewBinding);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ItemClienteBinding binding;

        public MyViewHolder(@NonNull ItemClienteBinding viewBinding) {
            super(viewBinding.getRoot());
            binding = viewBinding;
        }
    }
}
