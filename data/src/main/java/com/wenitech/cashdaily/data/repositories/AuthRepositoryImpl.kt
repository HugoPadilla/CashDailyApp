package com.wenitech.cashdaily.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.wenitech.cashdaily.data.entities.UserModel
import com.wenitech.cashdaily.data.remoteDataSource.RemoteDataSource
import com.wenitech.cashdaily.domain.constant.TypeAccountEnum
import com.wenitech.cashdaily.domain.repositories.AuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi
class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    private val remoteDataSource: RemoteDataSource
) : AuthRepository {

    override suspend fun getUisUser(): String? {
        return auth.uid
    }

    override suspend fun login(
        email: String,
        password: String
    ): Flow<com.wenitech.cashdaily.domain.common.ResultAuth<String>> = callbackFlow {
        offer(com.wenitech.cashdaily.domain.common.ResultAuth.loading(null))

        try {
            val firebaseUser = auth.signInWithEmailAndPassword(email, password).await().user
            if (firebaseUser != null) {
                offer(com.wenitech.cashdaily.domain.common.ResultAuth.success("Atenticacion exitosa"))
            }
        } catch (e: Throwable) {
            if (e is FirebaseAuthWeakPasswordException) {
                offer(com.wenitech.cashdaily.domain.common.ResultAuth.failed("Contraseña incorecta", "Error desde Twrowable"))
            } else {
                offer(com.wenitech.cashdaily.domain.common.ResultAuth.failed("Correo o contraseña invalida", e.message.toString()))
            }
        }

        awaitClose {

        }
    }

    override suspend fun singIn(
        name: String,
        email: String,
        password: String
    ): Flow<com.wenitech.cashdaily.domain.common.ResultAuth<String>> = callbackFlow {
        offer(com.wenitech.cashdaily.domain.common.ResultAuth.loading(null))
        try {
            val currentUser = auth.createUserWithEmailAndPassword(email, password).await().user
            if (currentUser != null) {
                val success = remoteDataSource.createAccount(
                    UserModel(
                        id = null, timestampCreation = null, businessName = "", email = email,
                        isFullProfile = false,
                        typeAccount = TypeAccountEnum.Admin.name,
                        fullName = name,
                        urlPhoto = ""
                    )
                )

                if (success) {
                    offer(com.wenitech.cashdaily.domain.common.ResultAuth.success("Cuenta creada corectamente"))
                } else {
                    offer(com.wenitech.cashdaily.domain.common.ResultAuth.failed("", ""))
                }
            }
        } catch (e: Throwable) {
            if (e is FirebaseAuthUserCollisionException) {
                offer(com.wenitech.cashdaily.domain.common.ResultAuth.collision("Esta cuenta ya existe", e.message.toString()))
            } else {
                offer(com.wenitech.cashdaily.domain.common.ResultAuth.failed("Error al crear tu cuenta", e.message.toString()))
            }
        }

        awaitClose {

        }
    }

    override suspend fun sendRecoverPassword(
        email: String
    ): Flow<com.wenitech.cashdaily.domain.common.ResultAuth<String>> = callbackFlow {
        offer(com.wenitech.cashdaily.domain.common.ResultAuth.loading(null))

        try {
            val result = auth.sendPasswordResetEmail(email).await()
            offer(com.wenitech.cashdaily.domain.common.ResultAuth.success("Correo de recuperacion enviado"))

        } catch (e: Throwable) {
            offer(com.wenitech.cashdaily.domain.common.ResultAuth.failed("Error al enviar el mensaje", e.message.toString()))
        }

        awaitClose {

        }
    }
}