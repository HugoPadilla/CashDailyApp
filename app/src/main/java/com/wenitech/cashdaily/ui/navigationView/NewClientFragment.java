package com.wenitech.cashdaily.ui.navigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FieldValue;
import com.wenitech.cashdaily.Data.model.Cliente;
import com.wenitech.cashdaily.R;
import com.wenitech.cashdaily.Util.StartedAddNewClient;
import com.wenitech.cashdaily.databinding.FragmentNewClientBinding;
import com.wenitech.cashdaily.viewModel.NewClientViewModel;

public class NewClientFragment extends Fragment {

    private FragmentNewClientBinding binding;
    private NewClientViewModel viewModel = new NewClientViewModel();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewClientBinding.inflate(LayoutInflater.from(inflater.getContext()), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(NewClientViewModel.class);
        setupAdapterAutoCompleteTextViewGender();

        binding.buttonSaveClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRegisterNewClient();
            }
        });

        viewModel.getMessenge().observe(getViewLifecycleOwner(), new Observer<StartedAddNewClient>() {
            @Override
            public void onChanged(StartedAddNewClient startedAddNewClient) {
                switch (startedAddNewClient.getStatedAddNewClient()){
                    case StartedAddNewClient.STATED_ADD_NEW_CLIENT_INIT:

                        break;
                    case StartedAddNewClient.STATED_ADD_NEW_CLIENT_PROCESS:
                        showProgressBar(true);
                        break;
                    case StartedAddNewClient.STATED_ADD_NEW_CLIENT_SUCCESS:
                        //Navigation.findNavController(view).navigateUp();
                        showProgressBar(false);
                        onSucces();
                        break;
                    case StartedAddNewClient.STATED_ADD_NEW_CLIENT_FAILED:
                        showProgressBar(false);
                        onError();
                        break;
                    case StartedAddNewClient.STATED_ADD_NEW_CLIENT_CANCEL:
                        showProgressBar(false);
                        break;
                }
            }
        });
    }

    private void setupAdapterAutoCompleteTextViewGender() {
        String[] opcionesGenero = new String[]{"Mujer", "Hombre", "No espesificar", "Otro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_dropdown, opcionesGenero);
        binding.autoCompleteTextViewGender.setAdapter(adapter);
    }


    public void initRegisterNewClient() {
        if (isFormValid()) {
            String fullName = binding.editTextFullName.getText().toString().trim();
            String idClient = binding.editTextId.getText().toString().trim();
            String gender = binding.autoCompleteTextViewGender.getText().toString().trim();
            String phoneNumber = binding.editTextPhoneNumber.getText().toString().trim();
            String city = binding.editTextCity.getText().toString().trim();
            String direction = binding.editTextDirection.getText().toString().trim();

            // Todo:Enviar Objeto Cliente al viewModel
            viewModel.addNewClient(fullName,idClient,gender,phoneNumber,city,direction);
        }
    }

    /**
     * Check if each editText is filled correctly
     * if not insert an error message.
     *
     * @return true if ok or false if not
     */
    private boolean isFormValid() {
        boolean valid = true;

        if (TextUtils.isEmpty(binding.editTextFullName.getText())) {
            binding.editTextFullName.setError("Escribe un nombre");
            valid = false;
        } else if (TextUtils.isEmpty(binding.editTextId.getText())) {
            binding.editTextId.setError("Identificacion del cliente");
            valid = false;
        } else if (TextUtils.isEmpty(binding.autoCompleteTextViewGender.getText())) {
            binding.autoCompleteTextViewGender.setError("Elige una opcion");
            valid = false;
        } else if (TextUtils.isEmpty(binding.editTextPhoneNumber.getText())) {
            binding.editTextPhoneNumber.setError("Escribe un numero de contacto");
            valid = false;
        } else if (TextUtils.isEmpty(binding.editTextCity.getText())) {
            binding.editTextCity.setError("Agrega un ciudad");
            valid = false;
        } else if (TextUtils.isEmpty(binding.editTextDirection.getText())) {
            binding.editTextDirection.setError("Direcion de residencia");
            valid = false;
        }
        return valid;
    }

    private void showProgressBar(Boolean showProgress) {
        if (showProgress) {
            binding.groupContent.setVisibility(View.INVISIBLE);
            binding.groupProgresbar.setVisibility(View.VISIBLE);
        } else {
            binding.groupContent.setVisibility(View.VISIBLE);
            binding.groupProgresbar.setVisibility(View.INVISIBLE);
        }
    }

    private void onSucces() {
        Snackbar.make(binding.getRoot(),"Cliente agregado", BaseTransientBottomBar.LENGTH_SHORT).show();
    }

    private void onError() {
        Snackbar.make(binding.getRoot(),"Ocurio un error", BaseTransientBottomBar.LENGTH_SHORT).show();
    }
}
