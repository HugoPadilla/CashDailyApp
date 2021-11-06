package com.wenitech.cashdaily.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.wenitech.cashdaily.data.entities.RutaModel
import com.wenitech.cashdaily.data.remoteDataSource.routes.Constant
import com.wenitech.cashdaily.domain.common.Resource
import com.wenitech.cashdaily.domain.entities.Ruta
import com.wenitech.cashdaily.domain.repositories.RoutesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi
class RouteRepositoryImpl(
    private val db: FirebaseFirestore,
    private val constant: Constant,
) : RoutesRepository {

    /**
     * LISTENER.
     *
     */
    override suspend fun getRoutes(): Flow<Resource<List<Ruta>>> = callbackFlow {
        offer(Resource.Loading())

        val queryCollection = constant
            .getCollectionRoutes()
            .orderBy(RutaModel::name.name, Query.Direction.DESCENDING)

        val listener = queryCollection.addSnapshotListener { documentSnapshot, error ->

            if (documentSnapshot != null) {
                offer(Resource.Success(documentSnapshot.toObjects(
                    Ruta::class.java)))
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
     * SINGLE OPERATION
     *
     */
    override suspend fun saveNewRoute(newRoute: Ruta): Flow<Resource<String>> = flow {
        emit(Resource.Loading())

        val document = constant.getCollectionRoutes().document()

        document.set(newRoute).await()

        emit(Resource.Success(""))

    }.catch {
        emit(Resource.Failure(it))
    }.flowOn(Dispatchers.IO)

    /**
     * SINGLE OPERATION
     *
     */
    override suspend fun updateNewRoute(
        idRoute: String,
        updateRoute: Ruta
    ): Flow<Resource<String>> = flow {
        emit(Resource.Loading())

        val document = constant.getDocumentRoute(idRoute)

        document.set(updateRoute).await()

        emit(Resource.Success(""))
    }.catch {
        emit(Resource.Failure(it))
    }.flowOn(Dispatchers.IO)

    /**
     * SINGLE OPERATION
     *
     */
    override suspend fun removeNewRoute(idRoute: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading())

        val document = constant.getDocumentRoute(idRoute)

        document.delete().await()

        emit(Resource.Success(""))

    }.catch {
        emit(Resource.Failure(it))
    }.flowOn(Dispatchers.IO)

}