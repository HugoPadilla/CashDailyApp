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
import com.wenitech.cashdaily.Model.Cuota;
import com.wenitech.cashdaily.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecyclerViewCuotaAdapter extends FirestoreRecyclerAdapter<Cuota, RecyclerViewCuotaAdapter.mViewHolder> {


    public RecyclerViewCuotaAdapter(@NonNull FirestoreRecyclerOptions<Cuota> options) {
        super(options);
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_credito, parent, false);
        return new mViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull mViewHolder holder, int position, @NonNull Cuota model) {

        holder.tv_numero_orden.setText("");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateSever = model.getbFechaCreacion().toDate();
        holder.tv_fecha.setText(format.format(dateSever));

        DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
        simbolo.setDecimalSeparator(',');
        simbolo.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("###,###.##", simbolo);

        holder.tv_cuota_credito.setText("$ " + decimalFormat.format(model.getcValorCuota()));
    }

    public class mViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView tv_numero_orden;
        private TextView tv_fecha;
        private TextView tv_cuota_credito;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardview_cuota);
            tv_numero_orden = itemView.findViewById(R.id.tv_numero_orden);
            tv_fecha = itemView.findViewById(R.id.tv_fecha_cuota);
            tv_cuota_credito = itemView.findViewById(R.id.tv_cuota_credito);
        }
    }

}
