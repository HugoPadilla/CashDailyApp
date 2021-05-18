package com.wenitech.cashdaily.data.firebase.model

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.*
import com.wenitech.cashdaily.data.model.Credito

class CreditLiveData(private val documentReference: DocumentReference) : LiveData<Credito?>(), EventListener<DocumentSnapshot?> {

    private var listenerRegistration: ListenerRegistration? = null

    override fun onEvent(documentSnapshot: DocumentSnapshot?, e: FirebaseFirestoreException?) {
        if (documentSnapshot != null && documentSnapshot.exists()) {
            val credito = documentSnapshot.toObject(Credito::class.java)
            value = credito
        } else {
            // Error document snapshot
        }
    }

    override fun onActive() {
        super.onActive()
        listenerRegistration = documentReference.addSnapshotListener(this)
    }

    override fun onInactive() {
        super.onInactive()
        if (!hasActiveObservers()) {
            listenerRegistration!!.remove()
            listenerRegistration = null
        }
    }
}