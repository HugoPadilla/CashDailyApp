package com.wenitech.cashdaily.ActivityMain.ActivityCaja;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

public class CajaModel implements CajaActivityInterface.model {

    private CajaActivityInterface.taskListener taskListener;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public CajaModel(CajaActivityInterface.taskListener taskListener) {
        this.taskListener = taskListener;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void agregarDineroCaja(final double valorAgregar) {

        final DocumentReference referenceCaja = db
                .collection("usuarios").document(mAuth.getUid())
                .collection("caja").document("myCaja");

        db.runTransaction(new Transaction.Function<Object>() {
            @Nullable
            @Override
            public Object apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                DocumentSnapshot snapshotCaja = transaction.get(referenceCaja);
                if (snapshotCaja.exists()) {
                    double valorCaja = snapshotCaja.getDouble("totalCaja");
                    transaction.update(referenceCaja, "totalCaja", valorCaja + valorAgregar);
                }
                return null;
            }

        }).addOnSuccessListener(new OnSuccessListener<Object>() {

            @Override
            public void onSuccess(Object o) {
                //Todo:Cuando se agrega corectamente
                taskListener.OnSucess("Se han agregado " + "$" + valorAgregar);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Todo: cuando hay error
                taskListener.OnError("No pudimos agregar el valor");
            }
        });
    }

    @Override
    public void retirarDineroCaja(final double valorRetirar) {
        final DocumentReference referenceCaja = db
                .collection("usuarios").document(mAuth.getUid())
                .collection("caja").document("myCaja");

        db.runTransaction(new Transaction.Function<Object>() {
            @Nullable
            @Override
            public Object apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshotCaja = transaction.get(referenceCaja);
                double valorCaja = snapshotCaja.getDouble("totalCaja");

                transaction.update(referenceCaja, "totalCaja", valorCaja - valorRetirar);
                return null;

            }
        }).addOnSuccessListener(new OnSuccessListener<Object>() {

            @Override
            public void onSuccess(Object o) {
                //Todo:CUnado se agrega corectamente
                taskListener.OnSucess("El retiro fue exitos");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Todo: cuando hay error
                taskListener.OnSucess("No pudimos retirar el dinero, intenta de nuevo");
            }
        });
    }
}
