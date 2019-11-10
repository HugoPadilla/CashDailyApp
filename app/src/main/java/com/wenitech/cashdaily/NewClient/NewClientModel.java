package com.wenitech.cashdaily.NewClient;

import android.media.MediaPlayer;
import android.widget.Toast;

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
import com.wenitech.cashdaily.Model.Cliente;

public class NewClientModel implements Interface.model {

    private Interface.taskListener taskListener;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore db;
    private DocumentReference documentReference;
    private Cliente cliente;

    public NewClientModel(final Interface.taskListener taskListener) {
        this.taskListener = taskListener;
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

    }


    @Override
    public void registrar(String identificacion, String nombre, String inicialNombre, String telefono,
                          String ubicacion, int valorPrestamo, int deudaPrestamo, boolean estado) {

        String inicial = nombre.substring(0,1).toUpperCase();

        cliente = new Cliente(Timestamp.now(),"usuarios/"+mAuth.getUid()+"/clientes/"+identificacion,identificacion,nombre,inicial,telefono,ubicacion,0,0,true);

        documentReference = db.collection("usuarios").document(mAuth.getUid())
                .collection("clientes").document(identificacion);

        documentReference.set(cliente).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    taskListener.succes();
                }else {

                    taskListener.error();
                }
            }
        });
    }
}
