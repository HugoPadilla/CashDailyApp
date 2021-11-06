package com.wenitech.cashdaily.data.remoteDataSource.routes

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

const val COLLECTION_USERS = "users"
const val COLLECTION_BOX = "box"
const val COLLECTION_MOVEMENTS = "movements"
const val COLLECTION_CLIENTS = "clients"
const val COLLECTION_CREDITS = "credits"
const val COLLECTION_QUOTAS = "quotas"
const val COLLECTION_ROUTE = "routes"

class Constant(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth,
) {

    // Profile user
    fun getCollectionUsers(): CollectionReference {
        return db.collection(COLLECTION_USERS)
    }

    fun getDocumentProfileUser(): DocumentReference {
        return getCollectionUsers().document(auth.uid.toString())
    }

    // Cash manage user
    fun getDocumentBox(): DocumentReference {
        return getDocumentProfileUser().collection(COLLECTION_BOX).document(auth.uid.toString())
    }

    fun getCollectionMovement(): CollectionReference {
        return getDocumentBox().collection(COLLECTION_MOVEMENTS)
    }

    // Clients of user
    fun getCollectionClients(): CollectionReference {
        return getDocumentProfileUser().collection(COLLECTION_CLIENTS)
    }

    fun getDocumentClient(idClient: String): DocumentReference {
        return getCollectionClients().document(idClient)
    }

    // Credit of client
    fun getCollectionCredits(idClient: String): CollectionReference {
        return getCollectionClients().document(idClient).collection(COLLECTION_CREDITS)
    }

    fun getDocumentCredit(idClient: String, idCredit: String): DocumentReference {
        return getCollectionCredits(idClient).document(idCredit)
    }

    fun getCollectionQuotas(idClient: String, idCredit: String): CollectionReference {
        return getDocumentCredit(idClient,idCredit).collection(COLLECTION_QUOTAS)
    }

    fun getCollectionRoutes(): CollectionReference {
        return getDocumentProfileUser().collection(COLLECTION_ROUTE)
    }

    fun getDocumentRoute(idRoute: String): DocumentReference {
        return getCollectionRoutes().document(idRoute)
    }
}
