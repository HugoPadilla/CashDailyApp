package com.wenitech.cashdaily.ActivityMain.ActivityClientes.ActivityNuevoCliente;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wenitech.cashdaily.Model.Cliente;

public class NewClientModel implements Interface.model {
    private Interface.taskListener taskListener;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore db;
    private DocumentReference REF_DOCUMENTO_CLIENTES;
    private Cliente mCliente;

    public NewClientModel(final Interface.taskListener taskListener) {
        this.taskListener = taskListener;
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void registrar(String nombreCliente, String identificacion, String genero, String telefono, String ciudad,String direccion) {
           // Establecer inicial del nombre del ciente
        String inicialNombre = nombreCliente.substring(0, 1).toUpperCase();
        REF_DOCUMENTO_CLIENTES = db.collection("usuarios").document(mAuth.getUid()).collection("clientes").document();
        mCliente = new Cliente(Timestamp.now(),nombreCliente,identificacion,inicialNombre,genero,telefono,ciudad,direccion);

        String id = REF_DOCUMENTO_CLIENTES.getPath();
        Log.d("ID_DOCUMENTO", "Id docuemtno" + id);

        REF_DOCUMENTO_CLIENTES.set(mCliente).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                taskListener.succes();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                taskListener.error();
            }
        });

    }
}