package com.wenitech.cashdaily.Data.LiveData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.wenitech.cashdaily.Data.model.Caja;

public class CajaLiveData extends LiveData<Caja> implements EventListener<DocumentSnapshot> {

    private DocumentReference documentReference;
    private ListenerRegistration listener;

    /**
     * Recibe la referencia del documento para tener una referencia local
     * @param documentReference
     */
    public CajaLiveData(DocumentReference documentReference){
        this.documentReference = documentReference;
    }

    @Override
    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
        if (documentSnapshot.exists() && documentSnapshot != null) {
            Caja caja = documentSnapshot.toObject(Caja.class);
            setValue(caja);
        } else {
            Caja newBox = new Caja(0.00);
            documentReference.set(newBox).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        // Send Messenger of box create
                    }
                }
            });
        }
    }

    @Override
    protected void onActive() {
        super.onActive();
        listener = documentReference.addSnapshotListener(this);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (!hasActiveObservers()){
            listener.remove();
            listener = null;
        }
    }

}
