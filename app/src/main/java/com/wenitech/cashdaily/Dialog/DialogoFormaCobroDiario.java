package com.wenitech.cashdaily.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.wenitech.cashdaily.R;

public class DialogoFormaCobroDiario extends AppCompatDialogFragment {

    private EditText editTextDiasPlazo, editTextValorCuota;
    private double totalPrestamo;

    public DialogoFormaCobroDiario(double totalPrestamo) {
        this.totalPrestamo = totalPrestamo;
    }

    private DialogoFormaCobroDiarioListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialogo_form_diario, null);

        builder.setView(view).setTitle("Forma de cobro diario")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editTextDiasPlazo.setError("Establece dias de plazo");
                    }
                });

        editTextDiasPlazo = view.findViewById(R.id.edit_dias_plazo_dialog);
        editTextValorCuota = view.findViewById(R.id.edit_valor_cuota_dialog);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogoFormaCobroDiarioListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Debe Implementar DialogoFormaCobroDiarioListener");
        }
    }

    public interface DialogoFormaCobroDiarioListener {
        void aplicarModalidaDiario(double DiasDePlazo, double valorCuota);
    }

}
