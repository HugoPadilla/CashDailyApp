package com.wenitech.cashdaily.data.firebase.model

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.*
import com.wenitech.cashdaily.data.model.Cliente

class ClientLiveData(val documentSnapshot: DocumentReference): LiveData<Cliente>(), EventListener<DocumentSnapshot> {

    private var listenerRegistration: ListenerRegistration? = null

    override fun onEvent(p0: DocumentSnapshot?, p1: FirebaseFirestoreException?) {
        if (p0 != null && p0.exists()){
            var client: Cliente? = p0.toObject(Cliente::class.java)
            setValue(client!!)
        }
    }

    override fun onActive() {
        super.onActive()
       listenerRegistration = documentSnapshot.addSnapshotListener(this)
    }

    override fun onInactive() {
        super.onInactive()
        if (!hasActiveObservers()){
            listenerRegistration!!.remove()
            listenerRegistration = null
        }
    }

}
