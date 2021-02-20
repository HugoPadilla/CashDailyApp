package com.wenitech.cashdaily.ui.navigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wenitech.cashdaily.Data.model.Credito;
import com.wenitech.cashdaily.R;
import com.wenitech.cashdaily.databinding.FragmentNewCreditBinding;
import com.wenitech.cashdaily.viewModel.CustomerCreditViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewCreditFragment extends Fragment {

    private FragmentNewCreditBinding binding;
    private CustomerCreditViewModel customerCreditViewModel;

    private String ID_CLIENT;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReferenceCredito;


    private Credito mCredito;

    // Contructor
    public NewCreditFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewCreditBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNewCreditVieModel();
        initAutoCompletTexViewPorcentaje();
        addListenerView();
        initEditTextDate();
        initObservers();
    }

    private void initObservers() {

        /*customerCreditViewModel.creditoLivesta.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.textView16.setText(s);
            }
        });*/
    }

    private void initNewCreditVieModel() {
        customerCreditViewModel = new ViewModelProvider(requireActivity()).get(CustomerCreditViewModel.class);
    }

    private void initAutoCompletTexViewPorcentaje() {
        /*String [] opciones = new String[]{"00","03","05","10","15","20","30"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.item_dropdown,opciones);
        autoCompleteTextViewPorcentaje.setAdapter(adapter);*/
    }

    private void addListenerView() {
        binding.radioGroupNuevoCreditoModalidad.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.radio_button_nuevo_credito_diario:
                    if (isFormularioCreditoValido()) {
                        binding.textInputLayoutNuevoCreditoPlazo.setVisibility(View.VISIBLE);
                        binding.textInputLayoutNuevoCreditoValorCuota.setVisibility(View.VISIBLE);
                        binding.textInputLayoutNuevoCreditoPlazo.setHint("Dias de plazo");
                        binding.textViewNoCobrarDias.setVisibility(View.VISIBLE);
                        binding.switchNuevoCreditoSabados.setVisibility(View.VISIBLE);
                        binding.switchNuevoCreditoDomingo.setVisibility(View.VISIBLE);
                    } else {
                        binding.radioGroupNuevoCreditoModalidad.clearCheck();
                        return;
                    }
                    break;
                case R.id.radio_button_nuevo_credito_semanal:
                   /* if (isFormularioCreditoValido()) {
                        textInputLayoutPlazo.setVisibility(View.VISIBLE);
                        textInputLayoutValorCuota.setVisibility(View.VISIBLE);
                        textInputLayoutPlazo.setHint("Semanas de plazo");
                        textViewNoCobrar.setVisibility(View.GONE);
                        aSwitchSabado.setVisibility(View.GONE);
                        aSwitchDomingo.setVisibility(View.GONE);
                    } else {
                        radioGroupModalidad.clearCheck();
                        return;
                    }*/
                    break;
                case R.id.radio_button_nuevo_credito_quincenal:
                    /*if (isFormularioCreditoValido()) {
                        textInputLayoutPlazo.setVisibility(View.VISIBLE);
                        textInputLayoutValorCuota.setVisibility(View.VISIBLE);
                        textInputLayoutPlazo.setHint("Quincenas de plazo");
                        textViewNoCobrar.setVisibility(View.GONE);
                        aSwitchSabado.setVisibility(View.GONE);
                        aSwitchDomingo.setVisibility(View.GONE);
                    } else {
                        radioGroupModalidad.clearCheck();
                        return;
                    }*/
                    break;
                case R.id.radio_button_nuevo_credito_mensual:
                   /* if (isFormularioCreditoValido()) {
                        textInputLayoutPlazo.setVisibility(View.VISIBLE);
                        textInputLayoutValorCuota.setVisibility(View.VISIBLE);
                        textInputLayoutPlazo.setHint("meses de plazo");
                        textViewNoCobrar.setVisibility(View.GONE);
                        aSwitchSabado.setVisibility(View.GONE);
                        aSwitchDomingo.setVisibility(View.GONE);
                    } else {
                        radioGroupModalidad.clearCheck();
                        return;
                    }*/
                    break;
            }
        }
    };

    private void initEditTextDate() {
        Date date = new Date();

        SimpleDateFormat formatFecha = new SimpleDateFormat("dd/mm/yyy");
        SimpleDateFormat formatHora = new SimpleDateFormat("hh:mm a");

        String fecha = formatFecha.format(date);
        String hora = formatHora.format(date);

        binding.editTextNuevoCreditoFecha.setText(fecha);
        binding.editTextNuevoCreditoHora.setText(hora);
    }

    public void ocultarView() {
        binding.constrainLayoutFormulario.setVisibility(View.GONE);
        binding.progressBarNuevoCredito.setVisibility(View.VISIBLE);
    }

    public void mostrarView() {
        binding.constrainLayoutFormulario.setVisibility(View.VISIBLE);
        binding.progressBarNuevoCredito.setVisibility(View.GONE);
    }

    public void registrarCredito() {
        if (isFormularioCreditoValido() && isFormularioModalidadValido()) {
            mCredito = new Credito();
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/mm/yyy hh:mm a");
            String stringFecha = binding.editTextNuevoCreditoFecha.getText().toString() + " " + binding.editTextNuevoCreditoHora.getText().toString();
            Date dateFecha = null;
            Timestamp fecha = Timestamp.now();
            try {
                dateFecha = formatoFecha.parse(stringFecha);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (dateFecha != null) {
                fecha = new Timestamp(dateFecha);
                mCredito.setFechaPretamo(fecha);
            }

            String valorPrestamo = binding.editTextNuevoCreditoValorPrestamo.getText().toString();
            String porcentaje = binding.autoCompleteTextNuevoCreditoPorcentaje.getText().toString();
            String totalCredito = binding.editTextNuevoCreditoTotalCredito.getText().toString();

            mCredito.setValorPrestamo(Double.parseDouble(valorPrestamo));
            mCredito.setPorcentaje(Double.parseDouble(porcentaje));
            mCredito.setTotalPrestamo(Double.parseDouble(totalCredito));
            mCredito.setDeudaPrestamo(Double.parseDouble(totalCredito));

            switch (binding.radioGroupNuevoCreditoModalidad.getCheckedRadioButtonId()) {
                case R.id.radio_button_nuevo_credito_diario:
                    mCredito.setModalida("Diario");

                    String plazoDias = binding.editTextNuevoCreditoPlazo.getText().toString().trim();
                    mCredito.setNumeroCuotas(Double.parseDouble(plazoDias));
                    String valorCuota = binding.editTextNuevoCreditoValorCuota.getText().toString().trim();
                    mCredito.setValorCuota(Double.parseDouble(valorCuota));

                    mCredito.setNoCobrarSabados(binding.switchNuevoCreditoSabados.isChecked());
                    mCredito.setNoCobrarDomingos(binding.switchNuevoCreditoDomingo.isChecked());

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dateFecha);
                    calendar.add(Calendar.DAY_OF_YEAR,1);

                    mCredito.setFechaProximaCuota(new Timestamp(calendar.getTime()));

                    break;
                case R.id.radio_button_nuevo_credito_semanal:
                    mCredito.setModalida("Semanal");
                    String plazoSemanas = binding.editTextNuevoCreditoPlazo.getText().toString().trim();
                    mCredito.setNumeroCuotas(Double.parseDouble(plazoSemanas));

                    mCredito.setNoCobrarSabados(false);
                    mCredito.setNoCobrarDomingos(false);
                    break;
                case R.id.radio_button_nuevo_credito_quincenal:
                    mCredito.setModalida("Quincenal");
                    String plazoQuicena = binding.editTextNuevoCreditoPlazo.getText().toString().trim();
                    mCredito.setNumeroCuotas(Double.parseDouble(plazoQuicena));

                    mCredito.setNoCobrarSabados(false);
                    mCredito.setNoCobrarDomingos(false);
                    break;
                case R.id.radio_button_nuevo_credito_mensual:
                    mCredito.setModalida("Mensual");
                    String plazoMensual = binding.editTextNuevoCreditoPlazo.getText().toString().trim();
                    mCredito.setNumeroCuotas(Double.parseDouble(plazoMensual));

                    mCredito.setNoCobrarSabados(false);
                    mCredito.setNoCobrarDomingos(false);
                    break;
            }
            // Todo: Llamar al metodo regitrar credito en viewModel
            customerCreditViewModel.addNewCreditOfClient(ID_CLIENT, mCredito);
        }
    }

    public boolean isFormularioCreditoValido() {
        boolean valido = true;
        if (TextUtils.isEmpty(binding.editTextNuevoCreditoFecha.getText().toString())) {
            binding.textInputLayoutNuevoCreditoFecha.setError("Seleciona una fecha");
            valido = false;
        } else if (TextUtils.isEmpty(binding.editTextNuevoCreditoHora.getText().toString())) {
            binding.textInputLayoutNuevoCreditoHora.setError("Establece una hora");
            valido = false;
        } else if (TextUtils.isEmpty(binding.editTextNuevoCreditoValorPrestamo.getText().toString())) {
            binding.textInputLayoutNuevoCreditoValorPrestamo.setError("Escribe un valor");
            valido = false;
        } else if (TextUtils.isEmpty(binding.autoCompleteTextNuevoCreditoPorcentaje.getText().toString())) {
            binding.textInputLayoutNuevoCreditoPorcentaje.setError("Establece un porcentaje");
            valido = false;
        } else if (TextUtils.isEmpty(binding.editTextNuevoCreditoTotalCredito.getText().toString())) {
            binding.textInputLayoutNuevoCreditoTotalCredito.setError("Total de credito");
            valido = false;
        }
        return valido;
    }

    public boolean isFormularioModalidadValido() {
        boolean isValid = true;
        if (TextUtils.isEmpty(binding.editTextNuevoCreditoPlazo.getText().toString())) {
            binding.textInputLayoutNuevoCreditoPlazo.setError("Estable una plazo");
            isValid = false;
        } else if (TextUtils.isEmpty(binding.editTextNuevoCreditoValorCuota.getText().toString())) {
            binding.textInputLayoutNuevoCreditoValorCuota.setError("Valor cuota");
            isValid = false;
        }
        return isValid;
    }

    public void onSucces() {
        Toast.makeText(getContext(), "Credito agregado", Toast.LENGTH_SHORT).show();
        // Todo: Navegar a la pantalla anterior
    }

    public void onError() {
        Toast.makeText(getContext(), "No es posible agregar el credito", Toast.LENGTH_SHORT).show();
    }
}
