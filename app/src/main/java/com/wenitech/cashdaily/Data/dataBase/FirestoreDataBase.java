package com.wenitech.cashdaily.Data.dataBase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.wenitech.cashdaily.Data.LiveData.CajaLiveData;
import com.wenitech.cashdaily.Data.model.Cliente;
import com.wenitech.cashdaily.Data.model.MovimientoCaja;
import com.wenitech.cashdaily.Util.StartedAddNewClient;

import java.util.Date;

public class FirestoreDataBase {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;

    private DocumentReference docReferenceUser;
    private DocumentReference docReferenceCaja;
    private DocumentReference docReferenceMovimientoCaja;
    private DocumentReference docReferenceClientes;

    private MutableLiveData<StartedAddNewClient> _startedAddNewClient = new MutableLiveData<>();
    /**
     * Constructor
     */
    public FirestoreDataBase() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();

        docReferenceUser = db.collection("usuarios").document(mAuth.getUid());
        docReferenceCaja = docReferenceUser.collection("caja").document("myCaja");
    }

    public LiveData<StartedAddNewClient> getStartedAddNewClient(){
        return _startedAddNewClient;
    }

    /**
     * Obtener el objeto caja del usuario en la base de datos
     *
     * @return CajaLiveData
     */
    public CajaLiveData getCaja() {
        return new CajaLiveData(docReferenceCaja);
    }

    /**
     * Agrega mas dinero a la caja utilizando la transsaciones de firestore
     *
     * @param valorAgregar
     */
    public void agregarDineroCaja(final double valorAgregar) {

        docReferenceMovimientoCaja = docReferenceCaja.collection("movimientos").document();

        db.runTransaction(new Transaction.Function<Object>() {
            @Nullable
            @Override
            public Object apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                DocumentSnapshot snapshotCaja = transaction.get(docReferenceCaja);
                if (snapshotCaja.exists()) {
                    double valorCaja = snapshotCaja.getDouble("totalCaja");
                    transaction.update(docReferenceCaja, "totalCaja", valorCaja + valorAgregar);

                    // Agregar nuevo registro de movimiento de caja
                    Date date = new Date(System.currentTimeMillis());
                    Timestamp timestamp = new Timestamp(date);

                    MovimientoCaja newMovimientoCaja = new MovimientoCaja(docReferenceMovimientoCaja.getId(), valorAgregar,
                            user.getDisplayName(),
                            timestamp,
                            true
                    );
                    transaction.set(docReferenceMovimientoCaja, newMovimientoCaja);
                }
                return null;
            }

        }).addOnSuccessListener(new OnSuccessListener<Object>() {

            @Override
            public void onSuccess(Object o) {
                //Todo:Cuando se agrega corectamente

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Todo: cuando hay error
            }
        });
    }

    /**
     * Retira dinero de la caja utilizando la transsaciones de firestore
     *
     * @param valorRetirar
     */
    public void retirarDineroCaja(final double valorRetirar) {

        docReferenceMovimientoCaja = docReferenceCaja.collection("movimientos").document();

        db.runTransaction(new Transaction.Function<Object>() {
            @Nullable
            @Override
            public Object apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshotCaja = transaction.get(docReferenceCaja);

                if (snapshotCaja.exists()) {
                    double valorCaja = snapshotCaja.getDouble("totalCaja");
                    transaction.update(docReferenceCaja, "totalCaja", valorCaja - valorRetirar);

                    // Agregar  novimiento de retiro de caja
                    Date date = new Date(System.currentTimeMillis());
                    Timestamp timestamp = new Timestamp(date);

                    MovimientoCaja newMovimientoCaja = new MovimientoCaja(docReferenceMovimientoCaja.getId(),
                            valorRetirar,
                            user.getDisplayName(),
                            timestamp,
                            false
                    );
                    transaction.set(docReferenceMovimientoCaja, newMovimientoCaja);
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


    // Todo: Data insertion Methods
    public void addNewClient(String fullName, String idClient, String gender, String phoneNumber, String city, String direction){
        _startedAddNewClient.setValue(defStartedAddNewClient(StartedAddNewClient.STATED_ADD_NEW_CLIENT_PROCESS));
        docReferenceClientes = docReferenceUser.collection("clientes").document();
        Cliente newClient = new Cliente(false,docReferenceClientes,fullName,idClient,gender,phoneNumber,city,direction);

        docReferenceClientes.set(newClient).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
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

    private StartedAddNewClient defStartedAddNewClient(int started){
        StartedAddNewClient startedAddNewClient = new StartedAddNewClient();
        startedAddNewClient.setStatedAddNewClient(started);
        return startedAddNewClient;
    }
}
