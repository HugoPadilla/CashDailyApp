package com.wenitech.cashdaily.ui.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wenitech.cashdaily.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentResetPasword#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentResetPasword extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentResetPasword() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentResetPasword.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentResetPasword newInstance(String param1, String param2) {
        FragmentResetPasword fragment = new FragmentResetPasword();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset_pasword, container, false);
    }

    /*private void cambiarContraseña() {
        if (mAuth.getCurrentUser() != null) {
            if (isFormularioValido()) {
                String passwordActual = editTextConstraseñaActual.getText().toString().trim();
                final String newPassword = editTextNuevaContraseña.getText().toString().trim();

                AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), passwordActual);
                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            actualizarPassword(newPassword);
                        } else {
                            configurarCuadroDialogo("Autenticacion", "No pudimo verificar tu identidad. Por favor, intenta nuevamnte", false);
                        }
                    }
                });
            }
        }
    }

    private void actualizarPassword(String newPassword) {
        user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    configurarCuadroDialogo("Actualizacion completada", "Su contraseña se ha cambiado con exito. Ahora puede acceder con sus nuevas credenciales", true);
                } else {
                    configurarCuadroDialogo("Tenemos un problemas", "No se pudo cambiar tu contraseña. Por favor, intenta nuevamente", false);
                }
            }
        });
    }

    private void configurarCuadroDialogo(String titulo, String mensaje, final boolean complete) {
        final MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(this);
        materialAlertDialogBuilder.setCancelable(false);
        materialAlertDialogBuilder.setTitle(titulo);
        materialAlertDialogBuilder.setMessage(mensaje);
        materialAlertDialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (complete) {
                    finishAfterTransition();
                }
            }
        });
        materialAlertDialogBuilder.show();
    }

    private boolean isFormularioValido() {
        boolean valido = true;

        String contraseñaActual = editTextConstraseñaActual.getText().toString().trim();
        String nuevaContraseña = editTextNuevaContraseña.getText().toString().trim();
        String confirmarContraseña = editTextConfirmarNuevaContraseña.getText().toString().trim();
        if (contraseñaActual.isEmpty()) {
            textInputLayoutContraseñaActual.setError("Escribe tu contraseña actual");
            valido = false;
        } else if (nuevaContraseña.isEmpty()) {
            textInputLayoutNuevaContraseña.setError("Escribe una contraseña nueva");
            valido = false;
        } else if (contraseñaActual.equals(nuevaContraseña)) {
            textInputLayoutNuevaContraseña.setError("Escribe una contraseña diferente");
            valido = false;
        } else if (nuevaContraseña.length() < 8) {
            textInputLayoutNuevaContraseña.setError("Debe tener al menos 8 caracteres");
            valido = false;
        } else if (confirmarContraseña.isEmpty()) {
            textInputLayoutConfirmarContraseña.setError("Confirma tu nueva contraseña");
            valido = false;
        } else if (!nuevaContraseña.equals(confirmarContraseña)) {
            textInputLayoutConfirmarContraseña.setError("Esta contraseña no coincide");
            editTextConfirmarNuevaContraseña.setText("");
            valido = false;
        }

        return valido;
    }*/
}