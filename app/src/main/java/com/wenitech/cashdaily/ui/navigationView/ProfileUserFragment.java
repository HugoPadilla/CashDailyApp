package com.wenitech.cashdaily.ui.navigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.wenitech.cashdaily.Data.model.UserApp;
import com.wenitech.cashdaily.R;
import com.wenitech.cashdaily.Data.remoteDatabase.MyFirebaseAuth;
import com.wenitech.cashdaily.databinding.FragmentPerfilBinding;
import com.wenitech.cashdaily.viewModel.ProfileUserViewModel;

public class ProfileUserFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private ProfileUserViewModel profileUserViewModel;
    private MyFirebaseAuth myAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPerfilBinding.inflate(LayoutInflater.from(inflater.getContext()), container, false);
        initFirebaseAuth();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initProfileUserViewModel();
        setupDateOfUserOnView();
        setupOnClickListener();
    }

    private void setupOnClickListener() {

    }

    private void initFirebaseAuth() {
        myAuth = MyFirebaseAuth.getInstance();

    }

    private void initProfileUserViewModel() {
        profileUserViewModel = new ViewModelProvider(this).get(ProfileUserViewModel.class);
    }

    private void setupDateOfUserOnView() {
        FirebaseUser user = myAuth.getAuth().getCurrentUser();
        if (user != null){
            profileUserViewModel.getUserProfile();
            profileUserViewModel.userAppLiveData.observe(getViewLifecycleOwner(), new Observer<UserApp>() {
                @Override
                public void onChanged(UserApp userApp) {

                    binding.textViewFullName.setText("userDisplayName");
                    binding.textViewEmail.setText("userEmail");
                    binding.textViewTypeUserAccount.setText(userApp.getTypeUserAccount());
                    binding.textViewTypeSubscription.setText(userApp.getTypeSubscription());
                }
            });
        }
    }

    /**
     * Dialog for replace FullName
     */
    private void openDialogChangeFullName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = getLayoutInflater().inflate(R.layout.dialogo_cambiar_nombre_usuario, null);
        final TextInputEditText editTextNuevoNombre = view.findViewById(R.id.edit_text_dialogo_nombre_usuario);

        builder.setView(view);
        builder.setTitle("Cambiar nombre");
        builder.setMessage("Este nombre sera visible para tus cobradores y puede que se use en algunas funciones de la App.");
        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nuevoNombre = editTextNuevoNombre.getText().toString().trim();
                if (nuevoNombre.isEmpty()) {
                    Toast.makeText(requireContext(), "No se han hecho cambios", Toast.LENGTH_SHORT).show();
                } else if (nuevoNombre.length() <= 3) {
                    Toast.makeText(requireContext(), "Escribe al menos 3 letras", Toast.LENGTH_SHORT).show();
                } else if (nuevoNombre.length() >= 35) {
                    Toast.makeText(requireContext(), "Nombre demasiado grande", Toast.LENGTH_SHORT).show();
                } else {
                    UserProfileChangeRequest update = new UserProfileChangeRequest.Builder().setDisplayName(nuevoNombre).build();
                    myAuth.getUser().updateProfile(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                setupDateOfUserOnView();
                                Toast.makeText(requireContext(), "Nombre Actualizado", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(requireContext(), "No fue posible cambiar los datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(requireContext(), "Cancelar", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}