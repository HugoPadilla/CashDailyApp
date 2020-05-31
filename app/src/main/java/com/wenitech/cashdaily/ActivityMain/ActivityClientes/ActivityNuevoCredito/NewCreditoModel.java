package com.wenitech.cashdaily.ActivityMain.ActivityClientes.ActivityNuevoCredito;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.wenitech.cashdaily.common.pojo.Credito;

import java.util.HashMap;
import java.util.Map;

public class NewCreditoModel implements NewCreditoInterface.model {
    private NewCreditoInterface.taskListener taskListener;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference documentReferenceCredito;

    public NewCreditoModel(NewCreditoInterface.taskListener taskListener) {
        this.taskListener = taskListener;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void registrarCredito(final Credito credito, String referenciaCliente) {
        final DocumentReference docReferenciaCaja = db.collection("usuarios").document(mAuth.getUid()).collection("caja").document("myCaja");
        documentReferenceCredito = db.document(referenciaCliente).collection("creditos").document();
        final DocumentReference docRefCliente = db.document(referenciaCliente);

        db.runTransaction(new Transaction.Function<String>() {
            @Nullable
            @Override
            public String apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(docReferenciaCaja);
                double valorCaja = snapshot.getDouble("totalCaja");
                double valorPrestamo = credito.getValorPrestamo();

                if (valorCaja >= valorPrestamo) {
                    Map<String, Object> updateCliente = new HashMap<>();
                    updateCliente.put("creditoActivo", true);
                    updateCliente.put("referenceCreditoActivo", documentReferenceCredito);

                    transaction.update(docReferenciaCaja, "totalCaja", valorCaja - credito.getValorPrestamo());
                    transaction.update(docRefCliente, updateCliente);
                    transaction.set(documentReferenceCredito, credito);
                    return "1";
                } else if (valorCaja <= 0) {
                    return "2";
                } else {
                    throw new FirebaseFirestoreException("Population too high",
                            FirebaseFirestoreException.Code.ABORTED);
                }
            }
        }).addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                switch (s) {
                    case "1":
                        taskListener.onSucces();
                        break;
                    case "2":
                        taskListener.onError();
                        break;
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }
}
