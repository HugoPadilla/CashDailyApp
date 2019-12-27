package com.wenitech.cashdaily.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.wenitech.cashdaily.R;

public class DialogoFormaCobroMensual extends AppCompatDialogFragment {

    private EditText editTextMesPlazo, editTextValorCuota;
    private Spinner spinnerDiasMes;
    private double totalPrestamo;

    public DialogoFormaCobroMensual(double totalPrestamo) {
        this.totalPrestamo = totalPrestamo;
    }

    private DialogoFormaCobroDiarioListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialogo_form_mensual, null);

        builder.setView(view).setTitle("Forma de cobro diario")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Es necesario un If
                        double mesPlazo = Integer.valueOf(editTextMesPlazo.getText().toString());
                        double valorCuota = Integer.valueOf(editTextValorCuota.getText().toString());
                        int diaMes = Integer.valueOf(spinnerDiasMes.getSelectedItem().toString());

                        if (mesPlazo <= 0){
                            editTextMesPlazo.setError("Estable el plazo");
                        }else if (valorCuota <= 0){
                            editTextValorCuota.setError("valor de la cuota");
                        }else {
                            listener.aplicarModalidaMensual(diaMes, mesPlazo, valorCuota);
                        }

                    }
                });

        editTextMesPlazo = view.findViewById(R.id.edit_mes_plazo_dialog);
        editTextValorCuota = view.findViewById(R.id.edit_valor_cuota_dialog);
        spinnerDiasMes = view.findViewById(R.id.spinner_dia_mes_dialog);

        String[] opciones = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, opciones);
        spinnerDiasMes.setAdapter(adapter);

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
        void aplicarModalidaMensual(int diasMesCobrar, double mesDePlazo, double valorCuota);
    }

}
