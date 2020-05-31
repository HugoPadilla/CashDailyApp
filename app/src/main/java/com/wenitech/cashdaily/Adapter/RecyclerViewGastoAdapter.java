package com.wenitech.cashdaily.Adapter;

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
import com.wenitech.cashdaily.common.pojo.Gasto;
import com.wenitech.cashdaily.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RecyclerViewGastoAdapter extends FirestoreRecyclerAdapter<Gasto, RecyclerViewGastoAdapter.mViewHolder> {

    public RecyclerViewGastoAdapter(@NonNull FirestoreRecyclerOptions<Gasto> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull final mViewHolder holder, int position, @NonNull Gasto model) {

        holder.valor.setText(String.valueOf(model.getValor()));
        holder.descripcion.setText(model.getDescripcion());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        Date date = model.getFecha().toDate();
        holder.fecha.setText(dateFormat.format(date));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gasto,parent,false);
        return new mViewHolder(view);
    }

    public class mViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;
        private Context context;
        private TextView valor;
        private TextView descripcion;
        private TextView fecha;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardViewItemGastoId);
            context = itemView.getContext();

            valor = itemView.findViewById(R.id.tv_valor);
            descripcion = itemView.findViewById(R.id.tv_descri);
            fecha = itemView.findViewById(R.id.tv_fecha);


        }
    }
}
