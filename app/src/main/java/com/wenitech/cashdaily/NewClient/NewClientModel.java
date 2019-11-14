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
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.wenitech.cashdaily.Model.Cliente;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class NewClientModel implements Interface.model {

    private Interface.taskListener taskListener;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore db;
    private CollectionReference collectionReference;
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

        String inicial = nombre.substring(0,1).toUpperCase();

        cliente = new Cliente(Timestamp.now(),null,identificacion,nombre,inicial,telefono,ubicacion,0,0,true);

        collectionReference = db.collection("usuarios").document(mAuth.getUid())
                .collection("clientes");

        collectionReference.add(cliente).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()){
                    DocumentReference documentReference = task.getResult();
                    documentReference.update("documentReference",documentReference);
                    taskListener.succes();
                }else {
                    taskListener.error();
                }
            }
        });
    }
}
