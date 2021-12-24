package com.wenitech.cashdaily.data.remoteDataSource

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.wenitech.cashdaily.data.entities.BoxModel
import com.wenitech.cashdaily.data.entities.CashTransactionsModel
import com.wenitech.cashdaily.data.remoteDataSource.routes.Constant
import com.wenitech.cashdaily.domain.common.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi
class BoxRemoteDataSourceImpl(
    private val db: FirebaseFirestore,
    private val constant: Constant
) : BoxRemoteDataSource {
    override suspend fun getUserBox(): Flow<Response<BoxModel>> = callbackFlow {
        offer(Response.Loading)
        val query = constant.getDocumentBox()

        val listener = query.addSnapshotListener { documentSnapshot, error ->
            if (documentSnapshot != null && documentSnapshot.exists()) {
                offer(
                    Response.Success(
                        documentSnapshot.toObject(
                            BoxModel::class.java
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
    } as Flow<Response<BoxModel>>

    override suspend fun getRecentMoves(): Flow<Response<List<CashTransactionsModel>>> =
        callbackFlow {
            offer(Response.Loading)
            val query = constant
                .getCollectionMovement()
                .orderBy(CashTransactionsModel::serverTimestamp.name, Query.Direction.DESCENDING)
                .limit(10)

            val listener = query.addSnapshotListener { documentSnapshot, error ->
                if (documentSnapshot != null) {
                    offer(
                        Response.Success(
                            documentSnapshot.toObjects(
                                CashTransactionsModel::class.java
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

    /**
     * Realiza una transaccion que verifica si el documento exiteste en firebase: si existe, obtiene
     * el dinero de la caja, luego le suma el valor de [value] y crea un nuevo registro del movimiento.
     * De lo contrario solo agregar el valor de [value] y guarda el registro del movivimiento
     *
     * @param value valor del dinero para agregar a la caja
     * @return Flow<Resource<String>> un flujo de [Response] de tipo [String]
     */
    override suspend fun saveMoneyOnBox(
        value: Double,
        description: String
    ): Flow<Response<String>> = flow {
        emit(Response.Loading)

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

        emit(Response.Success(""))

    }.catch {
        emit(Response.Error(it, it.message.toString()))
    }.flowOn(Dispatchers.IO)
}