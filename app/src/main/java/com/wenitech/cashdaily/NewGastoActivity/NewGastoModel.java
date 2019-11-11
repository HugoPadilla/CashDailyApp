package com.wenitech.cashdaily.NewGastoActivity;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wenitech.cashdaily.Model.Gasto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewGastoModel implements InterfaceNewGasto.model {

    InterfaceNewGasto.taskListener taskListener;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    private Gasto gasto;
    Date date;

    public NewGastoModel(InterfaceNewGasto.taskListener taskListener) {
        this.taskListener = taskListener;
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
    }
    @Override
    public void GuardarDatos(String valor, String fecha, String hora, String descripcion) {

        collectionReference = db.collection("usuarios").document(mAuth.getUid()).collection("gastos");



        Timestamp timestamp = Timestamp.now();
        gasto = new Gasto(Integer.parseInt(valor),descripcion,timestamp);

        collectionReference.add(gasto).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()){
                    taskListener.Sucess();
                }else {
                    taskListener.Error();
                }
            }
        });
    }
}
