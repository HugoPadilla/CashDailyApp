package com.wenitech.cashdaily.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.wenitech.cashdaily.data.entities.RutaDto
import com.wenitech.cashdaily.data.remoteDataSource.routes.Constant
import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.Ruta
import com.wenitech.cashdaily.domain.repositories.RoutesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@ExperimentalCoroutinesApi
class RouteRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val constant: Constant,
) : RoutesRepository {

    /**
     * LISTENER.
     *
     */
    override suspend fun getRoutes(): Flow<Response<List<Ruta>>> = callbackFlow {
        offer(Response.Loading)

        val queryCollection = constant
            .getCollectionRoutes()
            .orderBy(RutaDto::name.name, Query.Direction.DESCENDING)

        val listener = queryCollection.addSnapshotListener { documentSnapshot, error ->

            if (documentSnapshot != null) {
                offer(
                    Response.Success(
                        documentSnapshot.toObjects(
                            Ruta::class.java
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
     * SINGLE OPERATION
     *
     */
    override suspend fun saveNewRoute(newRoute: Ruta): Flow<Response<String>> = flow {
        emit(Response.Loading)

        val document = constant.getCollectionRoutes().document()

        document.set(newRoute).await()

        emit(Response.Success(""))

    }.catch {
        emit(Response.Error(it))
    }.flowOn(Dispatchers.IO)

    /**
     * SINGLE OPERATION
     *
     */
    override suspend fun updateNewRoute(
        idRoute: String,
        updateRoute: Ruta
    ): Flow<Response<String>> = flow {
        emit(Response.Loading)

        val document = constant.getDocumentRoute(idRoute)

        document.set(updateRoute).await()

        emit(Response.Success(""))
    }.catch {
        emit(Response.Error(it))
    }.flowOn(Dispatchers.IO)

    /**
     * SINGLE OPERATION
     *
     */
    override suspend fun removeNewRoute(idRoute: String): Flow<Response<String>> = flow {
        emit(Response.Loading)

        val document = constant.getDocumentRoute(idRoute)

        document.delete().await()

        emit(Response.Success(""))

    }.catch {
        emit(Response.Error(it))
    }.flowOn(Dispatchers.IO)

}