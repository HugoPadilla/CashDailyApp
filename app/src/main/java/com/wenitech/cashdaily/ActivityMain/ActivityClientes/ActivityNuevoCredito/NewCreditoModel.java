package com.wenitech.cashdaily.ActivityMain.ActivityClientes.ActivityNuevoCredito;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wenitech.cashdaily.Model.Credito;

public class NewCreditoModel implements NewCreditoInterface.model {
    private NewCreditoInterface.taskListener taskListener;

    private FirebaseFirestore db;
    private DocumentReference documentReferenceCredito;

    public NewCreditoModel(NewCreditoInterface.taskListener taskListener) {
        this.taskListener = taskListener;
        db =FirebaseFirestore.getInstance();
    }

    @Override
    public void registrarCredito(Credito credito,String referenciaCliente) {
        documentReferenceCredito = db.document(referenciaCliente).collection("creditos").document("credito");

        documentReferenceCredito.set(credito).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                taskListener.onSucces();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                taskListener.onError();
            }
        });
    }
}
