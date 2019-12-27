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

public class DialogoFormaCobroSemanal extends AppCompatDialogFragment {

    private EditText editTextSemanasPlazo, editTextValorCuota;
    private Spinner spinnerDiasSemana;
    private double totalPrestamo;

    public DialogoFormaCobroSemanal(double totalPrestamo) {
        this.totalPrestamo = totalPrestamo;
    }

    private DialogoFormaCobroDiarioListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialogo_form_semanal, null);

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
                        double semanasPlazo = Integer.valueOf(editTextSemanasPlazo.getText().toString());
                        double valorCuota = Integer.valueOf(editTextValorCuota.getText().toString());
                        String diaSemanaCobrar = spinnerDiasSemana.getSelectedItem().toString();
                        if (semanasPlazo <= 0){
                            editTextSemanasPlazo.setError("Establece las semana de plazo");
                        }else {
                            listener.aplicarModalidaSemanal(diaSemanaCobrar,semanasPlazo, valorCuota);
                        }

                    }
                });

        editTextSemanasPlazo = view.findViewById(R.id.edit_semanas_plazo_dialog);
        editTextValorCuota = view.findViewById(R.id.edit_valor_cuota_dialog);
        spinnerDiasSemana = view.findViewById(R.id.spinner_dia_semana_dialog);

        String [] opciones = {"Domingo","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,opciones);
        spinnerDiasSemana.setAdapter(adapter);

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
        void aplicarModalidaSemanal(String diasSemanaCobrar, double SemanasDePlazo, double valorCuota);
    }

}
