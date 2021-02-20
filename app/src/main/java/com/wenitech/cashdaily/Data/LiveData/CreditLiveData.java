package com.wenitech.cashdaily.Data.LiveData;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.wenitech.cashdaily.Data.model.Credito;

public class CreditLiveData extends LiveData<Credito> implements EventListener<DocumentSnapshot> {

    private final DocumentReference documentReference;
    private ListenerRegistration listenerRegistration;

    public CreditLiveData(DocumentReference documentReferenceCredit) {
        this.documentReference = documentReferenceCredit;
    }

    @Override
    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
        if (documentSnapshot != null && documentSnapshot.exists()) {
            Credito credito = documentSnapshot.toObject(Credito.class);
            setValue(credito);
        } else {
            // Error document snapshot
        }
    }

    @Override
    protected void onActive() {
        super.onActive();
        listenerRegistration = documentReference.addSnapshotListener(this);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (!hasActiveObservers()) {
            listenerRegistration.remove();
            listenerRegistration = null;
        }
    }
}
