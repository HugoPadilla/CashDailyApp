package com.wenitech.cashdaily.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wenitech.cashdaily.ClienteDetail.ClienteDetailActivity;
import com.wenitech.cashdaily.Model.Cliente;
import com.wenitech.cashdaily.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RecyclerViewHomeAdapter extends FirestoreRecyclerAdapter<Cliente, RecyclerViewHomeAdapter.MyViewHolder> {


    public RecyclerViewHomeAdapter(@NonNull FirestoreRecyclerOptions<Cliente> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final MyViewHolder holder, final int position, @NonNull final Cliente model) {
        holder.tv_userName.setText(model.getNombre());
        holder.tv_inicial.setText(model.getInicialNombre());

        if (model.getEstado()){
            holder.tv_estado.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        } else if (!model.getEstado()){
            holder.tv_estado.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_error_outline_red_24dp,0,0,0);
        }

        holder.tv_valorPrestamo.setText(String.valueOf(model.getValorPrestamo()));
        holder.tv_deudaPrestamo.setText(String.valueOf(model.getDeudaPrestamo()));

        Date dateSever = model.getFechaCreacion().toDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            holder.tv_fechaCreacion.setText(format.format(dateSever));

        holder.item_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(holder.context, ClienteDetailActivity.class);
                intent.putExtra("item_client",model.getDocumentReference());

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) holder.context,
                        holder.item_client,"itemClienteToDetail");

                holder.context.startActivity(intent,options.toBundle());
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cliente,parent,false);

        return new MyViewHolder(v);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        CollectionReference collectionReference;
        private Context context;
        private CardView item_client;
        private ImageView imageView;
        private TextView tv_userName;
        private TextView tv_inicial;
        private TextView tv_fechaCreacion;
        private TextView tv_valorPrestamo;
        private TextView tv_deudaPrestamo;
        private TextView tv_estado;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();
            imageView = itemView.findViewById(R.id.imageViewPerfil);

            item_client = itemView.findViewById(R.id.cardViewItemClienteId);

            tv_userName = itemView.findViewById(R.id.textViewName);
            tv_inicial = itemView.findViewById(R.id.textViewInicial);
            tv_fechaCreacion = itemView.findViewById(R.id.textViewFechaCreacion);
            tv_valorPrestamo = itemView.findViewById(R.id.textViewValorPrestamo);
            tv_deudaPrestamo = itemView.findViewById(R.id.textViewDeudaPrestamo);
            tv_estado = itemView.findViewById(R.id.textViewEstado);
        }
    }
}
