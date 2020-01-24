package com.wenitech.cashdaily.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.wenitech.cashdaily.ActivityClientes.ActivityDetallesCliente.ClienteDetailActivity;
import com.wenitech.cashdaily.Model.Cliente;
import com.wenitech.cashdaily.R;
public class RecyclerViewClienteAdapter extends FirestoreRecyclerAdapter<Cliente, RecyclerViewClienteAdapter.MyViewHolder> {

    public RecyclerViewClienteAdapter(@NonNull FirestoreRecyclerOptions<Cliente> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final MyViewHolder holder, final int position, @NonNull final Cliente model) {
        holder.textViewNombreCliente.setText(model.getNombreCliente());
        holder.textViewInicialNombre.setText(model.getInicialNombre());
        holder.textViewDireccionCliente.setText(model.getCiudad()+ ", " + model.getDireccion());
        holder.itemClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.context, ClienteDetailActivity.class);
                holder.context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cliente,parent,false);
        return new MyViewHolder(v);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        private Context context;
        private CardView itemClient;
        private ImageView imageView;
        private TextView textViewInicialNombre;
        private TextView textViewNombreCliente;
        private TextView textViewDireccionCliente;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemClient = itemView.findViewById(R.id.item_cliente);
            imageView = itemView.findViewById(R.id.imagen_view_item_cliente);
            textViewInicialNombre = itemView.findViewById(R.id.text_view_inicial_cliente);
            textViewNombreCliente = itemView.findViewById(R.id.text_view_nombre_cliente);
            textViewDireccionCliente = itemView.findViewById(R.id.text_view_direccion_cliente);
        }
    }
}
