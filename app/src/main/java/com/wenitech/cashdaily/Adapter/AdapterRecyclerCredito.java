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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdapterRecyclerCredito extends FirestoreRecyclerAdapter<Credito,AdapterRecyclerCredito.mViewHolder> {

    public AdapterRecyclerCredito(@NonNull FirestoreRecyclerOptions<Credito> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull mViewHolder holder, int position, @NonNull Credito model) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateSever = model.getFechaCuota().toDate();
        holder.tv_fecha.setText(format.format(dateSever));

        DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
        simbolo.setDecimalSeparator(',');
        simbolo.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("###,###.##",simbolo);

        holder.tv_cuota_credito.setText("$ " + decimalFormat.format(model.getValorCuota()));
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_credito,parent,false);
        return new mViewHolder(view);
    }

    public class mViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;
        private TextView tv_fecha;
        private TextView tv_cuota_credito;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardview_cuota);
            tv_fecha = itemView.findViewById(R.id.tv_fecha_cuota);
            tv_cuota_credito = itemView.findViewById(R.id.tv_cuota_credito);
        }
    }

}
