package com.wenitech.cashdaily.data.firebase.model

import androidx.lifecycle.LiveData
import com.wenitech.cashdaily.data.model.Caja
import com.google.firebase.firestore.*

class CajaLiveData(private val documentReference: DocumentReference) : LiveData<Caja>(), EventListener<DocumentSnapshot?> {

    private var listener: ListenerRegistration? = null

    override fun onEvent(documentSnapshot: DocumentSnapshot?, e: FirebaseFirestoreException?) {
        if (documentSnapshot != null && documentSnapshot.exists()) {
            val caja = documentSnapshot.toObject(Caja::class.java)
            value = caja!!
        } else {
            val newBox = Caja()
            newBox.totalCaja = 0.0
            newBox.gastos = 0.0
            documentReference.set(newBox).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Send Messenger of box create
                }
            }
        }
    }

    override fun onActive() {
        super.onActive()
        listener = documentReference.addSnapshotListener(this)
    }

    override fun onInactive() {
        super.onInactive()
        if (!hasActiveObservers()) {
            listener!!.remove()
            listener = null
        }
    }
}