package com.wenitech.cashdaily.NewClienteActivity;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.firestore.v1.Write;
import com.wenitech.cashdaily.Model.Cliente;

public class NewClientModel implements Interface.model {

    private Interface.taskListener taskListener;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore db;
    private DocumentReference REF_DOCUMENTO_CLIENTES;
    private Cliente cliente;

    public NewClientModel(final Interface.taskListener taskListener) {
        this.taskListener = taskListener;
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void registrar(final String identificacion, String nombre, String inicialNombre, String telefono,
                          String ubicacion, int valorPrestamo, int deudaPrestamo, boolean estado) {

            // Establecer inicial del nombre del ciente
        String inicial = nombre.substring(0, 1).toUpperCase();

            // Referencia a coleccion de cliente
        REF_DOCUMENTO_CLIENTES = db.collection("usuarios").document(mAuth.getUid()).collection("clientes").document();

            // Objeto Cliente con los atributos
        cliente = new Cliente(Timestamp.now(), REF_DOCUMENTO_CLIENTES, identificacion, nombre, inicial, telefono, ubicacion,
                0, 0, true);

        String id = REF_DOCUMENTO_CLIENTES.getPath();
        Log.d("ID_DOCUMENTO", "Id docuemtno" + id);

        REF_DOCUMENTO_CLIENTES.set(cliente).addOnSuccessListener(new OnSuccessListener<Void>() {
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