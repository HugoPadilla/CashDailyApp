package com.wenitech.cashdaily.data.remoteDataSource

import com.google.firebase.firestore.FirebaseFirestore
import com.wenitech.cashdaily.data.entities.BoxModel
import com.wenitech.cashdaily.data.entities.UserModel
import com.wenitech.cashdaily.data.remoteDataSource.routes.Constant
import com.wenitech.cashdaily.domain.common.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@ExperimentalCoroutinesApi
class UserRemoteDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val constant: Constant,
) : UserRemoteDataSource {
    override suspend fun createDocumentUser(userModel: UserModel): Boolean {
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

    override suspend fun getUserProfile(): Flow<Response<UserModel>> = callbackFlow {
        offer(Response.Loading)
        val queryDocument = constant.getDocumentProfileUser()

        val listener = queryDocument.addSnapshotListener { documentSnapshot, error ->
            if (documentSnapshot != null && documentSnapshot.exists()) {
                offer(Response.Success(documentSnapshot.toObject(UserModel::class.java)))
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
    } as Flow<Response<UserModel>>
}