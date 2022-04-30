package com.wenitech.cashdaily.data.remoteDataSource

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.wenitech.cashdaily.data.entities.BoxModel
import com.wenitech.cashdaily.data.entities.UserDto
import com.wenitech.cashdaily.data.remoteDataSource.routes.Constant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@ExperimentalCoroutinesApi
class UserRemoteDataSourceImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val constant: Constant,
) : UserRemoteDataSource {
    /**
     * Por medio de una escritrua en lote registra el perfil del usuario en la base de datos remota
     * y crear la rutas por defectos iniciales.
     *
     * @param userDto dto con la informacion del perfil del usuario
     */
    override suspend fun createUserProfile(userDto: UserDto) {
        val refUserApp = constant.getDocumentProfileUser()
        val refBox = constant.getDocumentBox()

        db.runBatch { batch ->

            batch.set(refUserApp, userDto)
            batch.set(refBox, BoxModel())

        }.await()
    }

    /**
     * Leer el perfile de usuario en la base de datos de Firestore
     *
     * @return DocumentSnapshot con el resultado de la operacion de lectura
     */
    override suspend fun getUserProfile(): DocumentSnapshot? {

        val queryDocument = constant.getDocumentProfileUser()

        return queryDocument.get().await()
    }
}