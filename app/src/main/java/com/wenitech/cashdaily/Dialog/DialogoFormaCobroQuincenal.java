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

public class DialogoFormaCobroQuincenal extends AppCompatDialogFragment {

    private EditText editTextQuincenasPlazo, editTextValorCuota;
    private Spinner spinnerDiaQuincenaInicial, spinnerDiasQuincenaFinal;
    private double totalPrestamo;

    public DialogoFormaCobroQuincenal(double totalPrestamo) {
        this.totalPrestamo = totalPrestamo;
    }

    private DialogoFormaCobroDiarioListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_dialogo_form_quincenal, null);

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
                        double quincenasPlazo = Integer.valueOf(editTextQuincenasPlazo.getText().toString());
                        double valorCuota = Integer.valueOf(editTextValorCuota.getText().toString());
                        int diaQuincenaInical = Integer.parseInt(spinnerDiaQuincenaInicial.getSelectedItem().toString());
                        int diaQuincenaFinal = Integer.parseInt(spinnerDiasQuincenaFinal.getSelectedItem().toString());

                        if (quincenasPlazo <= 0) {
                            editTextQuincenasPlazo.setError("Establece un plazo");
                        } else {
                            listener.aplicarModalidaQuincenal(diaQuincenaInical, diaQuincenaFinal, quincenasPlazo, valorCuota);
                        }
                    }
                });

        editTextQuincenasPlazo = view.findViewById(R.id.edit_quincenas_plazo_dialog);
        editTextValorCuota = view.findViewById(R.id.edit_valor_cuota_dialog);

        spinnerDiaQuincenaInicial = view.findViewById(R.id.spinner_dia_quincena_inicial);
        spinnerDiasQuincenaFinal = view.findViewById(R.id.spinner_dia_quincena_final);

        String[] opcionSpinnerInicial = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        ArrayAdapter<String> adapterInicial = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, opcionSpinnerInicial);
        String[] opcionSpinnerFinal = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        ArrayAdapter<String> adapterFinal = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, opcionSpinnerFinal);
        spinnerDiaQuincenaInicial.setAdapter(adapterInicial);
        spinnerDiasQuincenaFinal.setAdapter(adapterFinal);

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
        void aplicarModalidaQuincenal(int diaQuincenaInicial, int diaQuincenFinal, double SemanasDePlazo, double valorCuota);
    }

}
