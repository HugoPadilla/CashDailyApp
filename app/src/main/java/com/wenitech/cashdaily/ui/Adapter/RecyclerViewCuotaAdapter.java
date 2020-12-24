package com.wenitech.cashdaily.ui.Adapter;

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
import com.wenitech.cashdaily.Data.model.Cuota;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cuota, parent, false);
        return new mViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull mViewHolder holder, int position, @NonNull Cuota model) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateSever = model.getFechaCreacion().toDate();
        holder.textViewFecha.setText(format.format(dateSever));

        DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
        simbolo.setDecimalSeparator(',');
        simbolo.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("###,###.##", simbolo);
        String valorCuota = "$" + decimalFormat.format(model.getValorCuota());
        holder.textViewValorCuota.setText(valorCuota);

        holder.textViewNombreCreacion.setText(model.getNombreCreacion());

        if (model.getEstadoEditado().isEmpty() || model.getEstadoEditado().equals("Normal")){
            // Todo: estado normal
            holder.imageViewTrading.setImageResource(R.drawable.ic_asset_trending_up_verde);
        }else if (model.getEstadoEditado().equals("Editado")){
            // Todo: editado
            holder.textViewEditado.setText(" - " + model.getEstadoEditado());
            holder.imageViewTrading.setImageResource(R.drawable.ic_asset_trending_down_azul);
        }else if (model.getEstadoEditado().equals("Eliminado")){
            // Todo: eliminado
            holder.textViewEditado.setText(" - " + model.getEstadoEditado());
            holder.imageViewTrading.setImageResource(R.drawable.ic_asset_trending_down_rojo);
        }
    }

    public class mViewHolder extends RecyclerView.ViewHolder {

        private CardView cardViewItemCuota;
        private ImageView imageViewTrading;
        private TextView textViewValorCuota;
        private TextView textViewEditado;
        private TextView textViewNombreCreacion;
        private TextView textViewFecha;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);

            cardViewItemCuota = itemView.findViewById(R.id.cardview_item_cuota);
            imageViewTrading = itemView.findViewById(R.id.image_view_item_cuota_trading);
            textViewValorCuota = itemView.findViewById(R.id.text_view_item_cuota_valor_cuota);
            textViewEditado = itemView.findViewById(R.id.text_view_item_cuota_editar);
            textViewNombreCreacion = itemView.findViewById(R.id.text_view_item_cuota_nombre);
            textViewFecha = itemView.findViewById(R.id.text_view_item_cuota_fecha);
        }
    }

}
