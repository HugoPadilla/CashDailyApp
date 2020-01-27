package com.wenitech.cashdaily.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.wenitech.cashdaily.ActivityMain.ActivityClientes.ActivityCreditoCliente.CreditoClienteActivity;
import com.wenitech.cashdaily.ActivityMain.ActivityClientes.ActivityNuevoCredito.NewCreditActivity;
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
                if (model.isCreditoActivo()){
                    Intent intent = new Intent(holder.context, CreditoClienteActivity.class);
                    intent.putExtra("REFERENCIA_CLIENTE",model.getReferenceCliente().getPath());
                    holder.context.startActivity(intent);
                }else {
                    new MaterialAlertDialogBuilder(holder.context)
                            .setTitle("Realizar un nuevo credito?")
                            .setMessage(model.getNombreCliente() + ", " + "no tiene un credito activo pero puedes agregar uno nuevo")
                            .setNegativeButton("Cancelar",null)
                            .setPositiveButton("Nuevo Credito", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(holder.context, NewCreditActivity.class);
                                    intent.putExtra("REFERENCIA_CLIENTE",model.getReferenceCliente().getPath());
                                    holder.context.startActivity(intent);
                                }
                            }).show();
                }

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
