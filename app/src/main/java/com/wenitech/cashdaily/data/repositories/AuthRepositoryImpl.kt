package com.wenitech.cashdaily.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.wenitech.cashdaily.data.remoteDataSource.RemoteDataSource
import com.wenitech.cashdaily.commons.ResultAuth
import com.wenitech.cashdaily.domain.constant.TypeAccountEnum
import com.wenitech.cashdaily.domain.entities.User
import com.wenitech.cashdaily.domain.repositories.AuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@ExperimentalCoroutinesApi
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val remoteDataSource: RemoteDataSource
) : AuthRepository {

    override suspend fun getUisUser(): String? {
        return auth.uid
    }

    override suspend fun login(
        email: String,
        password: String
    ): Flow<ResultAuth<String>> = callbackFlow {
        offer(ResultAuth.loading(null))

        try {
            val firebaseUser = auth.signInWithEmailAndPassword(email, password).await().user
            if (firebaseUser != null) {
                offer(ResultAuth.success("Atenticacion exitosa"))
            }
        } catch (e: Throwable) {
            if (e is FirebaseAuthWeakPasswordException) {
                offer(ResultAuth.failed("Contraseña incorecta", "Error desde Twrowable"))
            } else {
                offer(ResultAuth.failed("Correo o contraseña invalida", e.message.toString()))
            }
        }

        awaitClose {

        }
    }

    override suspend fun singIn(
        name: String,
        email: String,
        password: String
    ): Flow<ResultAuth<String>> = callbackFlow {
        offer(ResultAuth.loading(null))
        try {
            val currentUser = auth.createUserWithEmailAndPassword(email, password).await().user
            if (currentUser != null) {
                val success = remoteDataSource.createAccount(currentUser.uid,
                    User(id = null, timestampCreation = null, businessName = "", email = email,
                        isFullProfile = false,
                        typeAccount = TypeAccountEnum.Admin.name,
                        fullName = name,
                        urlPhoto = ""
                    )
                )

                if (success) {
                    offer(ResultAuth.success("Cuenta creada corectamente"))
                } else {
                    offer(ResultAuth.failed("", ""))
                }
            }
        } catch (e: Throwable) {
            if (e is FirebaseAuthUserCollisionException) {
                offer(ResultAuth.collicion("Esta cuenta ya existe", e.message.toString()))
            } else {
                offer(ResultAuth.failed("Error al crear tu cuenta", e.message.toString()))
            }
        }

        awaitClose {

        }
    }
}