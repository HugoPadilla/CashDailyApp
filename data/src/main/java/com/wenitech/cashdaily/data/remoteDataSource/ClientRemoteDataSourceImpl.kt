package com.wenitech.cashdaily.data.remoteDataSource

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.wenitech.cashdaily.data.entities.ClientDto
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
@Suppress("UNCHECKED_CAST", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ClientRemoteDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val constant: Constant,
) : ClientRemoteDataSource {

    override suspend fun getAllClientsPaging(): Flow<Response<List<ClientDto>>> =
        callbackFlow {
            offer(Response.Loading)
            var lastClientReceived: DocumentSnapshot? = null

            val queryClients = constant.getCollectionClients()
                .orderBy(ClientDto::fullName.name, Query.Direction.ASCENDING)
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
                                ClientDto::class.java
                            )
                        )
                    )
                } else {
                    offer(Response.Success(listOf<ClientDto>()))
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

    override suspend fun getClientsCollectToday(): Flow<Response<List<ClientDto>>> =
        callbackFlow {
            offer(Response.Loading)
            val query = constant
                .getCollectionClients()
                .whereGreaterThan(ClientDto::paymentDate.name, Timestamp.now())
                .orderBy(ClientDto::fullName.name, Query.Direction.ASCENDING)
                .limit(10)

            val listener = query.addSnapshotListener { querySnapshot, error ->
                if (querySnapshot != null && !querySnapshot.isEmpty) {
                    offer(
                        Response.Success(
                            querySnapshot.toObjects(
                                ClientDto::class.java
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

    override suspend fun getBackCustomers(): Flow<Response<List<ClientDto>>> =
        callbackFlow {
            offer(Response.Loading)

            val query = constant
                .getCollectionClients()
                .whereLessThan(ClientDto::paymentDate.name, Timestamp.now())
                .orderBy(ClientDto::fullName.name, Query.Direction.ASCENDING)
                .limit(10)

            val listener = query.addSnapshotListener { value, error ->

                if (value != null && !value.isEmpty) {
                    offer(
                        Response.Success(
                            value.toObjects(
                                ClientDto::class.java
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

    override suspend fun getOverdueCustomers(): Flow<Response<List<ClientDto>>> =
        callbackFlow {
            offer(Response.Loading)

            val query = constant
                .getCollectionClients()
                .whereLessThan(ClientDto::finishDate.name, Timestamp.now())
                .orderBy(ClientDto::fullName.name, Query.Direction.ASCENDING)
                .limit(10)

            val listener = query.addSnapshotListener { value, error ->
                if (value != null && !value.isEmpty) {
                    offer(
                        Response.Success(
                            value.toObjects(
                                ClientDto::class.java
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

    override suspend fun saveNewClient(clientDto: ClientDto): Flow<Response<String>> = flow {
        emit(Response.Loading)

        val refNewClient = constant.getCollectionClients().document()

        refNewClient.set(clientDto).await()

        emit(Response.Success(refNewClient.id))

    }.catch {
        emit(Response.Error(it, it.message.toString()))
    }.flowOn(Dispatchers.IO)

    override suspend fun updateClient(
        idClient: String,
        updateClientDto: ClientDto
    ): Flow<Response<String>> = flow {
        emit(Response.Loading)

        val refClient = constant.getCollectionClients().document(idClient)

        refClient.set(updateClientDto).await()
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

    override suspend fun getClientById(idClient: String): Flow<Response<ClientDto>> =
        callbackFlow {
            offer(Response.Loading)

            val query = constant.getDocumentClient(idClient)

            val listener = query.addSnapshotListener { value, error ->

                if (value != null && value.exists()) {
                    offer(Response.Success(value.toObject(ClientDto::class.java)))
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
        } as Flow<Response<ClientDto>>
}