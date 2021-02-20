package com.wenitech.cashdaily.Data.remoteDatabase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.wenitech.cashdaily.Data.LiveData.CajaLiveData;
import com.wenitech.cashdaily.Data.LiveData.CreditLiveData;
import com.wenitech.cashdaily.Data.model.Cliente;
import com.wenitech.cashdaily.Data.model.Credito;
import com.wenitech.cashdaily.Data.model.Cuota;
import com.wenitech.cashdaily.Data.model.MovimientoCaja;
import com.wenitech.cashdaily.Data.model.UserApp;
import com.wenitech.cashdaily.Util.StartedAddNewClient;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FirestoreDataBase {

    private static FirestoreDataBase instance = null;

    private FirebaseFirestore db;

    private DocumentReference docReferenceUser;
    private DocumentReference docReferenceBox;
    private DocumentReference docReferenceBoxMovement;
    private DocumentReference docReferenceClient;

    private MutableLiveData<StartedAddNewClient> _startedAddNewClient = new MutableLiveData<>();

    /**
     * Constructor
     */
    public FirestoreDataBase() {
        db = FirebaseFirestore.getInstance();
    }

    /**
     * Singleton instance
     *
     * @return FirestoreDatabase
     */
    public static FirestoreDataBase getInstance() {
        if (instance == null) {
            instance = new FirestoreDataBase();
        }
        return instance;
    }

    // Todo: Get Started of Operations
    /**
     * Obtener el estado de la operacion agregar nuevo cliente
     *
     * @return
     */
    public LiveData<StartedAddNewClient> getStartedAddNewClient() {
        return _startedAddNewClient;
    }

    // Todo: Methods Get
    /**
     * get User Profile
     * @return
     */
    public LiveData<UserApp> getUserProfileDatabaBase() {
        return null;
    }

    /**
     * Obtener el objeto caja del usuario en la base de datos
     *
     * @return CajaLiveData
     */
    public CajaLiveData getCaja(FirebaseUser user) {

        docReferenceUser = db.collection("usuarios").document(user.getUid());
        docReferenceBox = docReferenceUser.collection("caja").document("myCaja");

        return new CajaLiveData(docReferenceBox);
    }

    /**
     * Recive una referenceCredit y debuelve un CrediLivedata
     * @param referenceCredit
     * @return
     */
    public CreditLiveData getCreditClient(String referenceCredit) {
        DocumentReference documentReferenceCredit = db.document(referenceCredit);
        return new CreditLiveData(documentReferenceCredit);
    }

    // Todo: Methods Set
    /**
     * Agrega mas dinero a la caja utilizando la transsaciones de firestore
     *
     * @param valorAgregar
     */
    public void agregarDineroCaja(final double valorAgregar, final FirebaseUser user) {

        docReferenceBoxMovement = docReferenceBox.collection("movimientos").document();

        db.runTransaction(new Transaction.Function<Object>() {
            @Nullable
            @Override
            public Object apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                DocumentSnapshot snapshotCaja = transaction.get(docReferenceBox);
                if (snapshotCaja.exists()) {
                    double valorCaja = snapshotCaja.getDouble("totalCaja");
                    transaction.update(docReferenceBox, "totalCaja", valorCaja + valorAgregar);

                    // Agregar nuevo registro de movimiento de caja
                    Date date = new Date(System.currentTimeMillis());
                    Timestamp timestamp = new Timestamp(date);

                    MovimientoCaja newMovimientoCaja = new MovimientoCaja(docReferenceBoxMovement.getId(), valorAgregar,
                            user.getDisplayName(),
                            timestamp,
                            true
                    );
                    transaction.set(docReferenceBoxMovement, newMovimientoCaja);
                }
                return null;
            }

        }).addOnSuccessListener(new OnSuccessListener<Object>() {

            @Override
            public void onSuccess(Object o) {
                //Todo:Cuando se agrega corectamente
                Log.d("FIREBASEADD", "Se agrego agrego dinero");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Todo: cuando hay error
                Log.d("FIREBASEADD", "No se agrego los datos" + e.getMessage());
            }
        });
    }

    /**
     * Retira dinero de la caja utilizando la transsaciones de firestore
     *
     * @param valorRetirar
     */
    public void retirarDineroCaja(final double valorRetirar, final FirebaseUser user) {

        docReferenceBoxMovement = docReferenceBox.collection("movimientos").document();

        db.runTransaction(new Transaction.Function<Object>() {
            @Nullable
            @Override
            public Object apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshotCaja = transaction.get(docReferenceBox);

                if (snapshotCaja.exists()) {
                    double valorCaja = snapshotCaja.getDouble("totalCaja");
                    transaction.update(docReferenceBox, "totalCaja", valorCaja - valorRetirar);

                    // Agregar  novimiento de retiro de caja
                    Date date = new Date(System.currentTimeMillis());
                    Timestamp timestamp = new Timestamp(date);

                    MovimientoCaja newMovimientoCaja = new MovimientoCaja(docReferenceBoxMovement.getId(),
                            valorRetirar,
                            user.getDisplayName(),
                            timestamp,
                            false
                    );
                    transaction.set(docReferenceBoxMovement, newMovimientoCaja);
                }
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Object>() {

            @Override
            public void onSuccess(Object o) {
                //Todo:CUnado se retira corectamente

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Todo: cuando hay error

            }
        });
    }

    /**
     * Agregar nuevo cliente a la base de datos
     *
     * @param fullName
     * @param idClient
     * @param gender
     * @param phoneNumber
     * @param city
     * @param direction
     */
    public void addNewClient(String fullName, String idClient, String gender, String phoneNumber, String city, String direction) {
        _startedAddNewClient.setValue(defStartedAddNewClient(StartedAddNewClient.STATED_ADD_NEW_CLIENT_PROCESS));
        docReferenceClient = docReferenceUser.collection("clientes").document();
        Cliente newClient = new Cliente(false, docReferenceClient, fullName, idClient, gender, phoneNumber, city, direction);

        docReferenceClient.set(newClient).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    _startedAddNewClient.setValue(defStartedAddNewClient(StartedAddNewClient.STATED_ADD_NEW_CLIENT_SUCCESS));
                } else {
                    _startedAddNewClient.setValue(defStartedAddNewClient(StartedAddNewClient.STATED_ADD_NEW_CLIENT_CANCEL));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                _startedAddNewClient.setValue(defStartedAddNewClient(StartedAddNewClient.STATED_ADD_NEW_CLIENT_FAILED));
            }
        });

    }

    /**
     * Add new Credit for Client
     * @param idClient
     * @param newCredit
     */
    public void addNewCreditOfClient(String idClient, Credito newCredit){
        // Todo: Codigo para agregar nuevo credito a un cliente en la base de datos Firestore

        /*final DocumentReference docReferenciaCaja = db.collection("usuarios").document(mAuth.getUid())
                .collection("caja").document("myCaja");

        documentReferenceCredito = db.collection("usuarios").document(mAuth.getUid())
                .collection("clientes").document(referenciaCliente)
                .collection("creditos").document();

        final DocumentReference docRefCliente = db.collection("usuarios").document(mAuth.getUid())
                .collection("clientes").document(referenciaCliente);

        db.runTransaction(new Transaction.Function<String>() {
            @Nullable
            @Override
            public String apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(docReferenciaCaja);
                double valorCaja = snapshot.getDouble("totalCaja");
                double valorPrestamo = credito.getValorPrestamo();

                if (valorCaja >= valorPrestamo) {
                    Map<String, Object> updateCliente = new HashMap<>();
                    updateCliente.put("creditActive", true);
                    updateCliente.put("documentReferenceCreditActive", documentReferenceCredito);

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
        });*/
    }

    /**
     * Agregar nueva newQuota al credito cliente
     *
     * @param newQuota
     * @param referenceCredit
     * @param idClient
     */
    public void addNewQuota(final Double newQuota, String idClient, String referenceCredit, final FirebaseUser user) {

        final DocumentReference documentReferenceUserApp = db.collection("usuarios").document(user.getUid());
        final DocumentReference documentReferenceBox = documentReferenceUserApp.collection("caja").document("myCaja");
        final DocumentReference documentReferenceClient = documentReferenceUserApp.collection("clientes").document(idClient);

        final DocumentReference documentReferenceCredit = db.document(referenceCredit);
        final DocumentReference documentReferenceQuote = db.document(referenceCredit).collection("cuotas").document();

        db.runTransaction(new Transaction.Function<Object>() {
            @Nullable
            @Override
            public Object apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                // Get valor actual dispobible de la caja,
                DocumentSnapshot snapshotCaja = transaction.get(documentReferenceBox);
                double totalCaja = snapshotCaja.getDouble("totalCaja");

                // Get customer debt value
                DocumentSnapshot snapshotCredito = transaction.get(documentReferenceCredit);
                double deudaPrestamo = snapshotCredito.getDouble("deudaPrestamo");

                // Calculate value of new post-debt
                double posDeuda = deudaPrestamo - newQuota;

                // Object new quote
                Cuota nuevaCuota = new Cuota(Timestamp.now(), user.getDisplayName(), "Normal", newQuota);

                if (newQuota <= deudaPrestamo) {
                    // Si posDeuda es meyor a cero no actualiza el credtio como inactivo,
                    // De lo contrario, si es igual a cero significa que ya no debe nada, por tanto
                    // lo marca en false
                    if (posDeuda > 0) {
                        transaction.update(documentReferenceCredit, "deudaPrestamo", posDeuda);
                        transaction.update(documentReferenceBox, "totalCaja", totalCaja + newQuota);
                        transaction.set(documentReferenceQuote, nuevaCuota);
                    } else if (posDeuda == 0) {
                        transaction.update(documentReferenceClient, "creditoActivo", false);
                        transaction.update(documentReferenceCredit, "deudaPrestamo", posDeuda);
                        transaction.update(documentReferenceBox, "totalCaja", totalCaja + newQuota);
                        transaction.set(documentReferenceQuote, nuevaCuota);
                    }
                } else {
                    // It is not possible to add a higher installment to the debt
                }

                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Object>() {
            @Override
            public void onSuccess(Object o) {
                // On Success

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // On Error

            }
        });
    }

    private StartedAddNewClient defStartedAddNewClient(int started) {
        StartedAddNewClient startedAddNewClient = new StartedAddNewClient();
        startedAddNewClient.setStatedAddNewClient(started);
        return startedAddNewClient;
    }
}
