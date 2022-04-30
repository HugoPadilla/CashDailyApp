package com.wenitech.cashdaily.data.remoteDataSource

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject
import com.wenitech.cashdaily.data.entities.BoxDto
import com.wenitech.cashdaily.data.entities.CashTransactionsDto
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
class BoxRemoteDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val constant: Constant
) : BoxRemoteDataSource {
    override fun getUserBox(): Flow<Response<BoxDto>> = callbackFlow {
        offer(Response.Loading)
        val query = constant.getDocumentBox()

        val listener = query.addSnapshotListener { documentSnapshot, error ->
            if (documentSnapshot != null && documentSnapshot.exists()) {
                offer(
                    Response.Success(documentSnapshot.toObject<BoxDto>()!!)
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

    override suspend fun getRecentMoves(): Flow<Response<List<CashTransactionsDto>>> =
        callbackFlow {
            offer(Response.Loading)
            val query = constant
                .getCollectionMovement()
                .orderBy(CashTransactionsDto::serverTimestamp.name, Query.Direction.DESCENDING)
                .limit(10)

            val listener = query.addSnapshotListener { documentSnapshot, error ->
                if (documentSnapshot != null) {
                    offer(
                        Response.Success(
                            documentSnapshot.toObjects(
                                CashTransactionsDto::class.java
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

                val serverBoxDto: BoxDto = boxSnapshot.toObject(BoxDto::class.java)!!

                if (value > 0) {
                    // Database writing
                    val cashTransactions =
                        CashTransactionsDto(null, null, description, true, value)
                    transaction.update(
                        refBox,
                        BoxDto::totalCash.name,
                        serverBoxDto.totalCash.plus(value)
                    )
                    transaction.set(refMovement, cashTransactions)
                }
                if (value < 0) {
                    val cashTransactions =
                        CashTransactionsDto(null, null, description, false, value)
                    transaction.update(
                        refBox,
                        BoxDto::totalCash.name,
                        serverBoxDto.totalCash.plus(value)
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