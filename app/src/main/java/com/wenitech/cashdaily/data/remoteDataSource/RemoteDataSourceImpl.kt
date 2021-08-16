package com.wenitech.cashdaily.data.remoteDataSource

import com.google.firebase.Timestamp
import com.google.firebase.firestore.*
import com.wenitech.cashdaily.commons.Resource
import com.wenitech.cashdaily.data.remoteDataSource.routes.Constant
import com.wenitech.cashdaily.domain.entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.collections.HashMap

@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RemoteDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val constant: Constant,
) : RemoteDataSource {

    @Suppress("UNREACHABLE_CODE")
    override suspend fun createAccount(uid: String, user: User): Boolean {
        return try {

            val refUserApp = constant.getDocumentProfileUser()
            val refBox = constant.getDocumentBox()

            db.runBatch { batch ->

                batch.set(refUserApp, user)
                batch.set(refBox, Box())

            }.await()
            true
        } catch (e: Throwable) {
            false
        }
    }

    override suspend fun getUserProfile(uid: String): Flow<Resource<User>> = callbackFlow {
        offer(Resource.Loading())
        val queryDocument = constant.getDocumentProfileUser()

        val listener = queryDocument.addSnapshotListener { documentSnapshot, error ->
            if (documentSnapshot != null && documentSnapshot.exists()) {
                offer(Resource.Success(documentSnapshot.toObject(User::class.java)))
            }

            error?.let {
                offer(Resource.Failure(it, it.message.toString()))
                cancel(it.message.toString())
            }
        }

        awaitClose {
            listener.remove()
            cancel()
        }
    } as Flow<Resource<User>>

    override suspend fun getUserBox(uid: String): Flow<Resource<Box>> = callbackFlow {
        offer(Resource.Loading())
        val query = constant.getDocumentBox()

        val listener = query.addSnapshotListener { documentSnapshot, error ->
            if (documentSnapshot != null && documentSnapshot.exists()) {
                offer(Resource.Success(documentSnapshot.toObject(Box::class.java)))
            }

            error?.let {
                offer(Resource.Failure(it))
                cancel(it.message.toString())
            }

        }
        awaitClose {
            listener.remove()
            cancel()
        }
    } as Flow<Resource<Box>>

    override suspend fun getRecentMoves(uid: String): Flow<Resource<List<CashTransactions>>> =
        callbackFlow {
            offer(Resource.Loading())
            val query = constant
                .getCollectionMovement()
                .orderBy(CashTransactions::serverTimestamp.name, Query.Direction.DESCENDING)
                .limit(10)

            val listener = query.addSnapshotListener { documentSnapshot, error ->
                if (documentSnapshot != null) {
                    offer(Resource.Success(documentSnapshot.toObjects(CashTransactions::class.java)))
                }

                error?.let {
                    offer(Resource.Failure(it))
                    cancel(it.message.toString())
                }
            }

            awaitClose {
                listener.remove()
                cancel()
            }
        }

    /**
     * Realiza una transaccion que verifica si el documento exiteste en firebase: si existe, obtiene
     * el dinero de la caja, luego le suma el valor de [value] y crea un nuevo registro del movimiento.
     * De lo contrario solo agregar el valor de [value] y guarda el registro del movivimiento
     *
     * @param value valor del dinero para agregar a la caja
     * @return Flow<Resource<String>> un flujo de [Resource] de tipo [String]
     */
    override suspend fun saveMoneyOnBox(
        uid: String,
        value: Double,
        description: String
    ): Flow<Resource<String>> = flow {
        emit(Resource.Loading())

        val refBox = constant.getDocumentBox()
        val refMovement = constant.getCollectionMovement().document()

        // Transaction Scope
        db.runTransaction { transaction ->

            // Database read
            val boxSnapshot: DocumentSnapshot = transaction.get(refBox)

            if (boxSnapshot.exists()) {

                val serverBox: Box = boxSnapshot.toObject(Box::class.java)!!

                if (value > 0) {
                    // Database writing
                    val cashTransactions =
                        CashTransactions(null, null, description, true, value)
                    transaction.update(refBox, Box::totalCash.name, serverBox.totalCash.plus(value))
                    transaction.set(refMovement, cashTransactions)
                }
                if (value < 0) {
                    val cashTransactions =
                        CashTransactions(null, null, description, false, value)
                    transaction.update(refBox, Box::totalCash.name, serverBox.totalCash.plus(value))
                    transaction.set(refMovement, cashTransactions)
                }

            }

        }.await()

        emit(Resource.Success(""))

    }.catch {
        emit(Resource.Failure(it, it.message.toString()))
    }.flowOn(Dispatchers.IO)

    override suspend fun getAllClientsPaging(): Flow<Resource<List<Client>>> =
        callbackFlow {
            offer(Resource.Loading())
            var lastClientReceived: DocumentSnapshot? = null

            val queryClients = constant.getCollectionClients()
                .orderBy(Client::fullName.name, Query.Direction.ASCENDING)
                .limit(10)

            val paginatedQuery =
                if (lastClientReceived != null) queryClients.startAfter(lastClientReceived)
                else queryClients

            val listenerRegistration = paginatedQuery.addSnapshotListener { querySnapshot, error ->

                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    lastClientReceived = querySnapshot.documents.last()
                    offer(Resource.Success(querySnapshot.toObjects(Client::class.java)))
                }

                error?.let {
                    offer(Resource.Failure(it, it.message.toString()))
                    cancel(it.message.toString())
                }
            }

            awaitClose {
                listenerRegistration.remove()
                cancel()
            }
        }

    override suspend fun getClientsCollectToday(): Flow<Resource<List<Client>>> =
        callbackFlow {
            offer(Resource.Loading())
            val query = constant
                .getCollectionClients()
                .whereGreaterThan(Client::paymentDate.name, Timestamp.now())
                .orderBy(Client::fullName.name, Query.Direction.ASCENDING)
                .limit(10)

            val listener = query.addSnapshotListener { querySnapshot, error ->
                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    offer(Resource.Success(querySnapshot.toObjects(Client::class.java)))
                }

                error?.let {
                    offer(Resource.Failure(it, it.message.toString()))
                    cancel(it.message.toString())
                }
            }

            awaitClose {
                listener.remove()
                cancel()
            }

        }

    override suspend fun getBackCustomers(): Flow<Resource<List<Client>>> =
        callbackFlow {
            offer(Resource.Loading())

            val query = constant
                .getCollectionClients()
                .whereLessThan(Client::paymentDate.name, Timestamp.now())
                .orderBy(Client::fullName.name, Query.Direction.ASCENDING)
                .limit(10)

            val listener = query.addSnapshotListener { value, error ->

                if (value != null && !value.isEmpty) {
                    offer(Resource.Success(value.toObjects(Client::class.java)))
                }

                error?.let {
                    offer(Resource.Failure(it, it.message.toString()))
                    cancel(it.message.toString())
                }
            }

            awaitClose {
                listener.remove()
                cancel()
            }
        }

    override suspend fun getOverdueCustomers(): Flow<Resource<List<Client>>> =
        callbackFlow {
            offer(Resource.Loading())

            val query = constant
                .getCollectionClients()
                .whereLessThan(Client::finishDate.name, Timestamp.now())
                .orderBy(Client::fullName.name, Query.Direction.ASCENDING)
                .limit(10)

            val listener = query.addSnapshotListener { value, error ->
                if (value != null && !value.isEmpty) {
                    offer(Resource.Success(value.toObjects(Client::class.java)))
                }

                error?.let {
                    offer(Resource.Failure(it, it.message.toString()))
                    cancel(it.message.toString())
                }
            }

            awaitClose {
                listener.remove()
                cancel()
            }
        }

    override suspend fun saveNewClient(client: Client): Flow<Resource<String>> = flow {
        emit(Resource.Loading())

        val refNewClient = constant.getCollectionClients().document()

        refNewClient.set(client).await()

        emit(Resource.Success(""))

    }.catch {
        emit(Resource.Failure(it, it.message.toString()))
    }.flowOn(Dispatchers.IO)

    override suspend fun updateClient(idClient: String, updateClient: Client): Flow<Resource<String>> = flow {
        emit(Resource.Loading())

        val refClient = constant.getCollectionClients().document(idClient)

        refClient.set(updateClient).await()
        emit(Resource.Success(refClient.id))

    }.catch {
        emit(Resource.Failure(it))
    }.flowOn(Dispatchers.IO)

    override suspend fun removeClient(idClient: String): Flow<Resource<String>> =
        flow {
            emit(Resource.Loading())

            val refClient = constant.getDocumentClient(idClient)

            refClient.delete().await()

            emit(Resource.Success(""))

        }.catch {
            emit(Resource.Failure(it, it.message.toString()))
        }.flowOn(Dispatchers.IO)

    override suspend fun getRecentCredits(
        uid: String,
        idClient: String
    ): Flow<Resource<List<Credit>>> = callbackFlow {
        offer(Resource.Loading())

        val query = constant.getCollectionCredits(idClient).limit(5)

        val listener = query.addSnapshotListener { snapshot, error ->
            if (snapshot != null && !snapshot.isEmpty) {
                offer(Resource.Success(snapshot.toObjects(Credit::class.java)))
            }

            error?.let {
                offer(Resource.Failure(it, it.message.toString()))
                cancel(it.message.toString())
            }
        }

        awaitClose {
            listener.remove()
            cancel()
        }
    }

    override suspend fun getCreditClient(
        uid: String,
        idClient: String,
        idCredit: String
    ): Flow<Resource<Credit>> = callbackFlow {
        offer(Resource.Loading())

        val refCreditClient = constant.getDocumentCredit(idClient, idCredit)

        // Register listener
        val listener = refCreditClient.addSnapshotListener { snapshot, error ->

            if (snapshot != null && snapshot.exists()) {
                offer(Resource.Success(snapshot.toObject(Credit::class.java)))
            }

            // If exception occurs, cancel this scope with exception message.
            error?.let {
                offer(Resource.Failure(it, it.message.toString()))
                cancel(it.message.toString())
            }
        }

        awaitClose {
            // This block is executed when producer channel is cancelled
            // This function resumes with a cancellation exception.

            // Dispose listener
            listener.remove()
            cancel()
        }

    } as Flow<Resource<Credit>>

    override suspend fun saveNewCredit(
        uid: String,
        idClient: String,
        newCredit: Credit
    ): Flow<Resource<String>> = flow {
        emit(Resource.Loading())

        val refBox = constant.getDocumentBox()
        val refClient = constant.getCollectionClients().document(idClient)
        val refNewCredit = constant.getCollectionCredits(idClient).document()

        // Transaction
        val transactions = db.runTransaction { transaction ->

            // Read database box
            val snapshot = transaction.get(refBox)
            val serverBox = snapshot.toObject(Box::class.java)

            val creditValue = newCredit.creditValue
            if (snapshot.exists() && serverBox != null) {

                if (serverBox.totalCash >= creditValue) {
                    // Write in database
                    val updateClient: MutableMap<String, Any> = HashMap()
                    updateClient[Client::creditActive.name] = true
                    updateClient[Client::refCredit.name] = refNewCredit
                    transaction.update(refClient, updateClient)

                    transaction.update(
                        refBox, Box::totalCash.name,
                        serverBox.totalCash - newCredit.creditValue
                    )

                    transaction.set(refNewCredit, newCredit)
                }

                refNewCredit.path
            } else {
                throw FirebaseFirestoreException(
                    "No hay suficiente dinero en la caja",
                    FirebaseFirestoreException.Code.ABORTED
                )
            }
        }.await()

        emit(Resource.Success(transactions))
    }.catch {
        emit(Resource.Failure(it, it.message.toString()))
    }.flowOn(Dispatchers.IO)


    override suspend fun getQuotaCredit(
        uid: String,
        idClient: String,
        idCredit: String
    ): Flow<Resource<List<Quota>>> = callbackFlow {
        offer(Resource.Loading())

        val query = constant
            .getCollectionQuotas(idClient, idCredit)
            .orderBy(Quota::timestamp.name, Query.Direction.DESCENDING)

        val listener = query.addSnapshotListener { querySnapshot, error ->
            if (querySnapshot != null && !querySnapshot.isEmpty) {
                offer(Resource.Success(querySnapshot.toObjects(Quota::class.java)))
            }

            error?.let {
                offer(Resource.Failure(it))
                cancel(it.message.toString())
            }
        }

        awaitClose {
            listener.remove()
            cancel()
        }
    }

    override suspend fun saveNewQuota(
        uid: String,
        idClient: String,
        idCredit: String,
        newQuota: Quota
    ): Flow<Resource<String>> = flow {
        emit(Resource.Loading())

        val refBox = constant.getDocumentBox()
        val refClient = constant.getDocumentClient(idClient)
        val refCredit = constant.getDocumentCredit(idClient, idCredit)
        val refNewQuote = constant.getCollectionQuotas(idClient, idCredit).document()

        db.runTransaction { transaction ->
            // Read database
            val snapshotBox = transaction.get(refBox)
            val serverBox = snapshotBox.toObject(Box::class.java)

            val snapshotCredit = transaction.get(refCredit)
            val serverCredit: Credit = snapshotCredit.toObject(Credit::class.java)!!

            // Calculate value of new post-debt
            val posCreditDebt = serverCredit.creditDebt - newQuota.value

            // se valida si hay una deuda en el credito actual
            if (serverCredit.creditDebt > 0) {
                if (posCreditDebt == 0.0) {
                    transaction.update(refClient, Client::creditActive.name, false)
                    transaction.update(refCredit, Credit::creditDebt.name, posCreditDebt)
                    transaction.update(
                        refBox, Box::totalCash.name,
                        serverBox!!.totalCash.plus(newQuota.value)
                    )

                    transaction.set(refNewQuote, newQuota)
                }

                if (posCreditDebt > 0) {
                    transaction.update(refCredit, Credit::creditDebt.name, posCreditDebt)
                    transaction.update(
                        refBox,
                        Box::totalCash.name,
                        serverBox!!.totalCash.plus(newQuota.value)
                    )

                    transaction.set(refNewQuote, newQuota)
                }
            } else {
                throw FirebaseFirestoreException(
                    "No hay deuda disponible",
                    FirebaseFirestoreException.Code.ABORTED
                )   // It is not possible to add a higher installment to the debt
            }

        }.await()

        emit(Resource.Success(""))
    }.catch {
        emit(Resource.Failure(it, it.message.toString()))
    }.flowOn(Dispatchers.IO)

    override suspend fun getReports(): Flow<Resource<ReportsDaily>> = callbackFlow {
        // Todo: Implement getReports
    }
}