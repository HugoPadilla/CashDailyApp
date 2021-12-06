package com.wenitech.cashdaily.data.remoteDataSource

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.wenitech.cashdaily.data.entities.*
import com.wenitech.cashdaily.data.remoteDataSource.routes.Constant
import com.wenitech.cashdaily.domain.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RemoteDataSourceImpl(
    private val db: FirebaseFirestore,
    private val constant: Constant,
) : RemoteDataSource {

    @Suppress("UNREACHABLE_CODE")
    override suspend fun createAccount(userModel: UserModel): Boolean {
        return try {

            val refUserApp = constant.getDocumentProfileUser()
            val refBox = constant.getDocumentBox()

            db.runBatch { batch ->

                batch.set(refUserApp, userModel)
                batch.set(refBox, BoxModel())

            }.await()
            true
        } catch (e: Throwable) {
            false
        }
    }

    override suspend fun getUserProfile(): Flow<Resource<UserModel>> = callbackFlow {
        offer(Resource.Loading())
        val queryDocument = constant.getDocumentProfileUser()

        val listener = queryDocument.addSnapshotListener { documentSnapshot, error ->
            if (documentSnapshot != null && documentSnapshot.exists()) {
                offer(
                    Resource.Success(
                        documentSnapshot.toObject(
                            UserModel::class.java
                        )
                    )
                )
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
    } as Flow<Resource<UserModel>>

    override suspend fun getUserBox(): Flow<Resource<BoxModel>> = callbackFlow {
        offer(Resource.Loading())
        val query = constant.getDocumentBox()

        val listener = query.addSnapshotListener { documentSnapshot, error ->
            if (documentSnapshot != null && documentSnapshot.exists()) {
                offer(
                    Resource.Success(
                        documentSnapshot.toObject(
                            BoxModel::class.java
                        )
                    )
                )
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
    } as Flow<Resource<BoxModel>>

    override suspend fun getRecentMoves(): Flow<Resource<List<CashTransactionsModel>>> =
        callbackFlow {
            offer(Resource.Loading())
            val query = constant
                .getCollectionMovement()
                .orderBy(CashTransactionsModel::serverTimestamp.name, Query.Direction.DESCENDING)
                .limit(10)

            val listener = query.addSnapshotListener { documentSnapshot, error ->
                if (documentSnapshot != null) {
                    offer(
                        Resource.Success(
                            documentSnapshot.toObjects(
                                CashTransactionsModel::class.java
                            )
                        )
                    )
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

                val serverBoxModel: BoxModel = boxSnapshot.toObject(BoxModel::class.java)!!

                if (value > 0) {
                    // Database writing
                    val cashTransactions =
                        CashTransactionsModel(null, null, description, true, value)
                    transaction.update(
                        refBox,
                        BoxModel::totalCash.name,
                        serverBoxModel.totalCash.plus(value)
                    )
                    transaction.set(refMovement, cashTransactions)
                }
                if (value < 0) {
                    val cashTransactions =
                        CashTransactionsModel(null, null, description, false, value)
                    transaction.update(
                        refBox,
                        BoxModel::totalCash.name,
                        serverBoxModel.totalCash.plus(value)
                    )
                    transaction.set(refMovement, cashTransactions)
                }

            }

        }.await()

        emit(Resource.Success(""))

    }.catch {
        emit(Resource.Failure(it, it.message.toString()))
    }.flowOn(Dispatchers.IO)

    override suspend fun getAllClientsPaging(): Flow<Resource<List<ClientModel>>> =
        callbackFlow {
            offer(Resource.Loading())
            var lastClientReceived: DocumentSnapshot? = null

            val queryClients = constant.getCollectionClients()
                .orderBy(ClientModel::fullName.name, Query.Direction.ASCENDING)
                .limit(10)

            val paginatedQuery =
                if (lastClientReceived != null) queryClients.startAfter(lastClientReceived)
                else queryClients

            val listenerRegistration = paginatedQuery.addSnapshotListener { querySnapshot, error ->

                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    lastClientReceived = querySnapshot.documents.last()
                    offer(
                        Resource.Success(
                            querySnapshot.toObjects(
                                ClientModel::class.java
                            )
                        )
                    )
                } else {
                    offer(Resource.Success(listOf<ClientModel>()))
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

    override suspend fun getClientsCollectToday(): Flow<Resource<List<ClientModel>>> =
        callbackFlow {
            offer(Resource.Loading())
            val query = constant
                .getCollectionClients()
                .whereGreaterThan(ClientModel::paymentDate.name, Timestamp.now())
                .orderBy(ClientModel::fullName.name, Query.Direction.ASCENDING)
                .limit(10)

            val listener = query.addSnapshotListener { querySnapshot, error ->
                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    offer(
                        Resource.Success(
                            querySnapshot.toObjects(
                                ClientModel::class.java
                            )
                        )
                    )
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

    override suspend fun getBackCustomers(): Flow<Resource<List<ClientModel>>> =
        callbackFlow {
            offer(Resource.Loading())

            val query = constant
                .getCollectionClients()
                .whereLessThan(ClientModel::paymentDate.name, Timestamp.now())
                .orderBy(ClientModel::fullName.name, Query.Direction.ASCENDING)
                .limit(10)

            val listener = query.addSnapshotListener { value, error ->

                if (value != null && !value.isEmpty) {
                    offer(
                        Resource.Success(
                            value.toObjects(
                                ClientModel::class.java
                            )
                        )
                    )
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

    override suspend fun getOverdueCustomers(): Flow<Resource<List<ClientModel>>> =
        callbackFlow {
            offer(Resource.Loading())

            val query = constant
                .getCollectionClients()
                .whereLessThan(ClientModel::finishDate.name, Timestamp.now())
                .orderBy(ClientModel::fullName.name, Query.Direction.ASCENDING)
                .limit(10)

            val listener = query.addSnapshotListener { value, error ->
                if (value != null && !value.isEmpty) {
                    offer(
                        Resource.Success(
                            value.toObjects(
                                ClientModel::class.java
                            )
                        )
                    )
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

    override suspend fun saveNewClient(clientModel: ClientModel): Flow<Resource<String>> = flow {
        emit(Resource.Loading())

        val refNewClient = constant.getCollectionClients().document()

        refNewClient.set(clientModel).await()

        emit(Resource.Success(""))

    }.catch {
        emit(Resource.Failure(it, it.message.toString()))
    }.flowOn(Dispatchers.IO)

    override suspend fun updateClient(
        idClient: String,
        updateClientModel: ClientModel
    ): Flow<Resource<String>> = flow {
        emit(Resource.Loading())

        val refClient = constant.getCollectionClients().document(idClient)

        refClient.set(updateClientModel).await()
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
    ): Flow<Resource<List<CreditModel>>> = callbackFlow {
        offer(Resource.Loading())

        val query = constant.getCollectionCredits(idClient).limit(5)

        val listener = query.addSnapshotListener { snapshot, error ->
            if (snapshot != null && !snapshot.isEmpty) {
                offer(
                    Resource.Success(
                        snapshot.toObjects(
                            CreditModel::class.java
                        )
                    )
                )
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
        idClient: String,
        idCredit: String
    ): Flow<Resource<CreditModel>> = callbackFlow {
        offer(Resource.Loading())

        val refCreditClient = constant.getDocumentCredit(idClient, idCredit)

        // Register listener
        val listener = refCreditClient.addSnapshotListener { snapshot, error ->

            if (snapshot != null && snapshot.exists()) {
                offer(Resource.Success(snapshot.toObject(CreditModel::class.java)))
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

    } as Flow<Resource<CreditModel>>

    override suspend fun saveNewCredit(
        idClient: String,
        newCreditModel: CreditModel
    ): Flow<Resource<String>> = flow {
        emit(Resource.Loading())

        val refBox = constant.getDocumentBox()
        val refClient = constant.getCollectionClients().document(idClient)
        val refNewCredit = constant.getCollectionCredits(idClient).document()

        // Transaction
        val transactions = db.runTransaction { transaction ->

            // Read database box
            val snapshot = transaction.get(refBox)
            val serverBox = snapshot.toObject(BoxModel::class.java)

            val creditValue = newCreditModel.creditValue
            if (snapshot.exists() && serverBox != null) {

                if (serverBox.totalCash >= creditValue) {
                    // Write in database
                    val updateClient: MutableMap<String, Any> = HashMap()
                    updateClient[ClientModel::creditActive.name] = true
                    updateClient[ClientModel::refCredit.name] = refNewCredit
                    transaction.update(refClient, updateClient)

                    transaction.update(
                        refBox, BoxModel::totalCash.name,
                        serverBox.totalCash - newCreditModel.creditValue
                    )

                    transaction.set(refNewCredit, newCreditModel)
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
        idClient: String,
        idCredit: String
    ): Flow<Resource<List<QuotaModel>>> = callbackFlow {
        offer(Resource.Loading())

        val query = constant
            .getCollectionQuotas(idClient, idCredit)
            .orderBy(QuotaModel::timestamp.name, Query.Direction.DESCENDING)

        val listener = query.addSnapshotListener { querySnapshot, error ->
            if (querySnapshot != null && !querySnapshot.isEmpty) {
                offer(
                    Resource.Success(
                        querySnapshot.toObjects(
                            QuotaModel::class.java
                        )
                    )
                )
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
        idClient: String,
        idCredit: String,
        newQuotaModel: QuotaModel
    ): Flow<Resource<String>> = flow {
        emit(Resource.Loading())

        val refBox = constant.getDocumentBox()
        val refClient = constant.getDocumentClient(idClient)
        val refCredit = constant.getDocumentCredit(idClient, idCredit)
        val refNewQuote = constant.getCollectionQuotas(idClient, idCredit).document()

        db.runTransaction { transaction ->
            // Read database
            val snapshotBox = transaction.get(refBox)
            val serverBox = snapshotBox.toObject(BoxModel::class.java)

            val snapshotCredit = transaction.get(refCredit)
            val serverCreditModel: CreditModel = snapshotCredit.toObject(CreditModel::class.java)!!

            // Calculate value of new post-debt
            val posCreditDebt = serverCreditModel.creditDebt - newQuotaModel.value

            // se valida si hay una deuda en el credito actual
            if (serverCreditModel.creditDebt > 0) {
                if (posCreditDebt == 0.0) {
                    transaction.update(refClient, ClientModel::creditActive.name, false)
                    transaction.update(refCredit, CreditModel::creditDebt.name, posCreditDebt)
                    transaction.update(
                        refBox, BoxModel::totalCash.name,
                        serverBox!!.totalCash.plus(newQuotaModel.value)
                    )

                    transaction.set(refNewQuote, newQuotaModel)
                }

                if (posCreditDebt > 0) {
                    transaction.update(refCredit, CreditModel::creditDebt.name, posCreditDebt)
                    transaction.update(
                        refBox,
                        BoxModel::totalCash.name,
                        serverBox!!.totalCash.plus(newQuotaModel.value)
                    )

                    transaction.set(refNewQuote, newQuotaModel)
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

    override suspend fun getReports(): Flow<Resource<ReportsDailyModel>> = callbackFlow {
        // Todo: Implement getReports
    }

    override suspend fun getClientById(idClient: String): Flow<Resource<ClientModel>> =
        callbackFlow {
            offer(Resource.Loading())

            val query = constant.getDocumentClient(idClient)

            val listener = query.addSnapshotListener { value, error ->

                if (value != null && value.exists()) {
                    offer(Resource.Success(value.toObject(ClientModel::class.java)))
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
        } as Flow<Resource<ClientModel>>
}