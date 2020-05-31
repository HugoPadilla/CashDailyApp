package com.wenitech.cashdaily.ActivityMain.ActivityClientes.ActivityCreditoCliente;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.wenitech.cashdaily.common.pojo.Cuota;

public class CreditoClienteModel implements CreditoClienteInterface.model {
    private CreditoClienteInterface.taskListener taskListener;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public CreditoClienteModel(CreditoClienteInterface.taskListener taskListener) {
        this.taskListener = taskListener;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void agregarNuevaCuota(final Cuota cuota, String referenciaCredito, String referenciasCliente) {

        final DocumentReference documentCaja = db.collection("usuarios").document(mAuth.getUid()).collection("caja").document("myCaja");
        final DocumentReference documentReferenceCredito = db.document(referenciaCredito);
        final DocumentReference documentReferenceCuota = db.document(referenciaCredito).collection("cuotas").document();
        final DocumentReference documentReferenceCliente = db.document(referenciasCliente);


        db.runTransaction(new Transaction.Function<Object>() {
            @Nullable
            @Override
            public Object apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                DocumentSnapshot snapshotCredito = transaction.get(documentReferenceCredito);
                double deudaPrestamo = snapshotCredito.getDouble("deudaPrestamo");
                DocumentSnapshot snapshotCaja = transaction.get(documentCaja);
                double totalCaja = snapshotCaja.getDouble("totalCaja");
                double valorCuota = cuota.getValorCuota();
                double nuevaDeuda = deudaPrestamo - valorCuota;
                Cuota nuevaCuota = new Cuota(Timestamp.now(), "Admin", "Normal", valorCuota);


                if (valorCuota <= deudaPrestamo) {
                    if (nuevaDeuda > 0){
                        transaction.update(documentReferenceCredito, "deudaPrestamo", nuevaDeuda);
                        transaction.update(documentCaja, "totalCaja", totalCaja + valorCuota);
                        transaction.set(documentReferenceCuota, nuevaCuota);
                    }else if (nuevaDeuda == 0){
                        transaction.update(documentReferenceCliente, "creditoActivo", false);
                        transaction.update(documentReferenceCredito, "deudaPrestamo", nuevaDeuda);
                        transaction.update(documentCaja, "totalCaja", totalCaja + valorCuota);
                        transaction.set(documentReferenceCuota, nuevaCuota);
                    }
                }

                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Object>() {
            @Override
            public void onSuccess(Object o) {
                taskListener.onSucces("Cuota agregada con exito");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                taskListener.onError("No fue posible realizar la transacion.");
            }
        });
    }
}
