package com.wenitech.cashdaily.data.firebase.routes

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

data class FirestoreRouters(
        val db: FirebaseFirestore,
        val auth: FirebaseAuth,
        val user: FirebaseUser
) {

    val usuariosCollection: String = "usuarios"
    val clientCollection: String = "clientes"
    val cajaCollection: String = "caja"
    val myCajaDoc: String = "myCaja"

    fun getDocUserApp(): DocumentReference {
        return db.collection(usuariosCollection).document(user.uid)
    }

    fun getCollectionBoxMovimento(): CollectionReference {
        return db.collection(usuariosCollection).document(user.uid).collection(cajaCollection).document(myCajaDoc).collection("movimientos")
    }

    fun getCollectionClients(): CollectionReference {
        return db.collection(usuariosCollection).document(user.uid).collection(clientCollection)
    }

    fun getDocClientById(idClient:String): DocumentReference{
        return db.collection(usuariosCollection).document(user.uid).collection(clientCollection).document(idClient)
    }

    fun getDocRefBox(): DocumentReference {
        return db.collection(usuariosCollection).document(user.uid).collection(cajaCollection).document(myCajaDoc)
    }
}
