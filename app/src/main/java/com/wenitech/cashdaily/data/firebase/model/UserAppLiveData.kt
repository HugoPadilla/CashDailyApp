package com.wenitech.cashdaily.data.firebase.model

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.*
import com.wenitech.cashdaily.data.model.User

class UserAppLiveData(private var documentReference: DocumentReference) : LiveData<User?>(), EventListener<DocumentSnapshot?> {

    var listenerRegistration: ListenerRegistration? = null

    override fun onEvent(documentSnapshot: DocumentSnapshot?, e: FirebaseFirestoreException?) {
        if (documentSnapshot != null && documentSnapshot.exists()) {
            val userApp = documentSnapshot.toObject(User::class.java)
            value = userApp
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