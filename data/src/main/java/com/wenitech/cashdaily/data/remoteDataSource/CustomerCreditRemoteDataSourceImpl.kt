package com.wenitech.cashdaily.data.remoteDataSource

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.wenitech.cashdaily.data.entities.BoxDto
import com.wenitech.cashdaily.data.entities.ClientDto
import com.wenitech.cashdaily.data.entities.CreditModel
import com.wenitech.cashdaily.data.entities.QuotaDto
import com.wenitech.cashdaily.data.remoteDataSource.routes.Constant
import com.wenitech.cashdaily.domain.common.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@ExperimentalCoroutinesApi
class CustomerCreditRemoteDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val constant: Constant
) : CustomerCreditRemoteDataSource {
    override suspend fun getRecentCredits(
        uid: String,
        idClient: String
    ): Flow<Response<List<CreditModel>>> = callbackFlow {
        offer(Response.Loading)

        val query = constant.getCollectionCredits(idClient).limit(5)

        val listener = query.addSnapshotListener { snapshot, error ->
            if (snapshot != null && !snapshot.isEmpty) {
                offer(
                    Response.Success(
                        snapshot.toObjects(
                            CreditModel::class.java
                        )
                    )
                )
            }

            error?.let {
                offer(Response.Error(it, it.message.toString()))
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
    ): Flow<Response<CreditModel>> = callbackFlow {
        offer(Response.Loading)

        val refCreditClient = constant.getDocumentCredit(idClient, idCredit)

        // Register listener
        val listener = refCreditClient.addSnapshotListener { snapshot, error ->

            if (snapshot != null && snapshot.exists()) {
                offer(Response.Success(snapshot.toObject(CreditModel::class.java)))
            }

            // If exception occurs, cancel this scope with exception message.
            error?.let {
                offer(Response.Error(it, it.message.toString()))
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

    } as Flow<Response<CreditModel>>

    override suspend fun saveNewCredit(
        idClient: String,
        newCreditModel: CreditModel
    ): Flow<Response<String>> = flow {
        emit(Response.Loading)

        val refBox = constant.getDocumentBox()
        val refClient = constant.getCollectionClients().document(idClient)
        val refNewCredit = constant.getCollectionCredits(idClient).document()

        // Transaction
        val transactions = db.runTransaction { transaction ->

            // Read database box
            val snapshot = transaction.get(refBox)
            val serverBox = snapshot.toObject(BoxDto::class.java)

            val creditValue = newCreditModel.creditValue
            if (snapshot.exists() && serverBox != null) {

                if (serverBox.totalCash >= creditValue) {
                    // Write in database
                    val updateClient: MutableMap<String, Any> = HashMap()
                    updateClient[ClientDto::creditActive.name] = true
                    updateClient[ClientDto::refCredit.name] = refNewCredit
                    transaction.update(refClient, updateClient)

                    transaction.update(
                        refBox, BoxDto::totalCash.name,
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

        emit(Response.Success(transactions))
    }.catch {
        emit(Response.Error(it, it.message.toString()))
    }.flowOn(Dispatchers.IO)


    override suspend fun getQuotaCredit(
        idClient: String,
        idCredit: String
    ): Flow<Response<List<QuotaDto>>> = callbackFlow {
        offer(Response.Loading)

        val query = constant
            .getCollectionQuotas(idClient, idCredit)
            .orderBy(QuotaDto::timestamp.name, Query.Direction.DESCENDING)

        val listener = query.addSnapshotListener { querySnapshot, error ->
            if (querySnapshot != null && !querySnapshot.isEmpty) {
                offer(
                    Response.Success(
                        querySnapshot.toObjects(
                            QuotaDto::class.java
                        )
                    )
                )
            }

            error?.let {
                offer(Response.Error(it))
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
        newQuotaDto: QuotaDto
    ): Flow<Response<String>> = flow {
        emit(Response.Loading)

        val refBox = constant.getDocumentBox()
        val refClient = constant.getDocumentClient(idClient)
        val refCredit = constant.getDocumentCredit(idClient, idCredit)
        val refNewQuote = constant.getCollectionQuotas(idClient, idCredit).document()

        db.runTransaction { transaction ->
            // Read database
            val snapshotBox = transaction.get(refBox)
            val serverBox = snapshotBox.toObject(BoxDto::class.java)

            val snapshotCredit = transaction.get(refCredit)
            val serverCreditModel: CreditModel = snapshotCredit.toObject(CreditModel::class.java)!!

            // Calculate value of new post-debt
            val posCreditDebt = serverCreditModel.creditDebt - newQuotaDto.value

            // se valida si hay una deuda en el credito actual
            if (serverCreditModel.creditDebt > 0) {
                if (posCreditDebt == 0.0) {
                    transaction.update(refClient, ClientDto::creditActive.name, false)
                    transaction.update(refCredit, CreditModel::creditDebt.name, posCreditDebt)
                    transaction.update(
                        refBox, BoxDto::totalCash.name,
                        serverBox!!.totalCash.plus(newQuotaDto.value)
                    )

                    transaction.set(refNewQuote, newQuotaDto)
                }

                if (posCreditDebt > 0) {
                    transaction.update(refCredit, CreditModel::creditDebt.name, posCreditDebt)
                    transaction.update(
                        refBox,
                        BoxDto::totalCash.name,
                        serverBox!!.totalCash.plus(newQuotaDto.value)
                    )

                    transaction.set(refNewQuote, newQuotaDto)
                }
            } else {
                throw FirebaseFirestoreException(
                    "No hay deuda disponible",
                    FirebaseFirestoreException.Code.ABORTED
                )   // It is not possible to add a higher installment to the debt
            }

        }.await()

        emit(Response.Success(""))
    }.catch {
        emit(Response.Error(it, it.message.toString()))
    }.flowOn(Dispatchers.IO)
}