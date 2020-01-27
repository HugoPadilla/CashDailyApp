package com.wenitech.cashdaily.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.wenitech.cashdaily.Model.Credito;
import com.wenitech.cashdaily.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.Inflater;

public class RecyclerViewCreditosAdapter extends FirestoreRecyclerAdapter<Credito, RecyclerViewCreditosAdapter.mViewHolder> {


    public RecyclerViewCreditosAdapter(@NonNull FirestoreRecyclerOptions<Credito> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull mViewHolder holder, int position, @NonNull Credito model) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        Date fechaCredito = model.getFechaPretamo().toDate();
        holder.tvFecha.setText(simpleDateFormat.format(fechaCredito));

    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prestamo, parent, false);

        return new mViewHolder(view);
    }

    public class mViewHolder extends RecyclerView.ViewHolder {

        CardView itemPrestamo;
        TextView tvFecha;
        TextView tvEstado;
        TextView tvTotalPrestamo;
        TextView tvModalidad;
        TextView tvNumeroCuotas;
        TextView tvPorcentaje;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);

            itemPrestamo = itemView.findViewById(R.id.cardview_item_pretamo);
            tvFecha = itemView.findViewById(R.id.tv_fecha_prestamo);
            tvEstado = itemView.findViewById(R.id.textView_estado);
            tvTotalPrestamo = itemView.findViewById(R.id.tv_total_prestamo);
            tvModalidad = itemView.findViewById(R.id.tv_modalidad);
            tvNumeroCuotas = itemView.findViewById(R.id.tv_numero_cuotas);
            tvPorcentaje = itemView.findViewById(R.id.tv_porcentaje);

        }
    }

}
