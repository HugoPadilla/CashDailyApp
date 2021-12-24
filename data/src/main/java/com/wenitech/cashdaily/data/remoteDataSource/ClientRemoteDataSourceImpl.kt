package com.wenitech.cashdaily.data.remoteDataSource

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.wenitech.cashdaily.data.entities.ClientModel
import com.wenitech.cashdaily.data.remoteDataSource.routes.Constant
import com.wenitech.cashdaily.domain.common.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi
@Suppress("UNCHECKED_CAST", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ClientRemoteDataSourceImpl(
    private val db: FirebaseFirestore,
    private val constant: Constant,
) : ClientRemoteDataSource {

    override suspend fun getAllClientsPaging(): Flow<Response<List<ClientModel>>> =
        callbackFlow {
            offer(Response.Loading)
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
                        Response.Success(
                            querySnapshot.toObjects(
                                ClientModel::class.java
                            )
                        )
                    )
                } else {
                    offer(Response.Success(listOf<ClientModel>()))
                }

                error?.let {
                    offer(Response.Error(it, it.message.toString()))
                    cancel(it.message.toString())
                }
            }

            awaitClose {
                listenerRegistration.remove()
                cancel()
            }
        }

    override suspend fun getClientsCollectToday(): Flow<Response<List<ClientModel>>> =
        callbackFlow {
            offer(Response.Loading)
            val query = constant
                .getCollectionClients()
                .whereGreaterThan(ClientModel::paymentDate.name, Timestamp.now())
                .orderBy(ClientModel::fullName.name, Query.Direction.ASCENDING)
                .limit(10)

            val listener = query.addSnapshotListener { querySnapshot, error ->
                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    offer(
                        Response.Success(
                            querySnapshot.toObjects(
                                ClientModel::class.java
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

    override suspend fun getBackCustomers(): Flow<Response<List<ClientModel>>> =
        callbackFlow {
            offer(Response.Loading)

            val query = constant
                .getCollectionClients()
                .whereLessThan(ClientModel::paymentDate.name, Timestamp.now())
                .orderBy(ClientModel::fullName.name, Query.Direction.ASCENDING)
                .limit(10)

            val listener = query.addSnapshotListener { value, error ->

                if (value != null && !value.isEmpty) {
                    offer(
                        Response.Success(
                            value.toObjects(
                                ClientModel::class.java
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

    override suspend fun getOverdueCustomers(): Flow<Response<List<ClientModel>>> =
        callbackFlow {
            offer(Response.Loading)

            val query = constant
                .getCollectionClients()
                .whereLessThan(ClientModel::finishDate.name, Timestamp.now())
                .orderBy(ClientModel::fullName.name, Query.Direction.ASCENDING)
                .limit(10)

            val listener = query.addSnapshotListener { value, error ->
                if (value != null && !value.isEmpty) {
                    offer(
                        Response.Success(
                            value.toObjects(
                                ClientModel::class.java
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

    override suspend fun saveNewClient(clientModel: ClientModel): Flow<Response<String>> = flow {
        emit(Response.Loading)

        val refNewClient = constant.getCollectionClients().document()

        refNewClient.set(clientModel).await()

        emit(Response.Success(refNewClient.id))

    }.catch {
        emit(Response.Error(it, it.message.toString()))
    }.flowOn(Dispatchers.IO)

    override suspend fun updateClient(
        idClient: String,
        updateClientModel: ClientModel
    ): Flow<Response<String>> = flow {
        emit(Response.Loading)

        val refClient = constant.getCollectionClients().document(idClient)

        refClient.set(updateClientModel).await()
        emit(Response.Success(refClient.id))

    }.catch {
        emit(Response.Error(it))
    }.flowOn(Dispatchers.IO)

    override suspend fun removeClient(idClient: String): Flow<Response<String>> =
        flow {
            emit(Response.Loading)

            val refClient = constant.getDocumentClient(idClient)

            refClient.delete().await()

            emit(Response.Success(""))

        }.catch {
            emit(Response.Error(it, it.message.toString()))
        }.flowOn(Dispatchers.IO)

    override suspend fun getClientById(idClient: String): Flow<Response<ClientModel>> =
        callbackFlow {
            offer(Response.Loading)

            val query = constant.getDocumentClient(idClient)

            val listener = query.addSnapshotListener { value, error ->

                if (value != null && value.exists()) {
                    offer(Response.Success(value.toObject(ClientModel::class.java)))
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
        } as Flow<Response<ClientModel>>
}