package com.wenitech.cashdaily.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.wenitech.cashdaily.domain.common.ResultAuth
import com.wenitech.cashdaily.domain.repositories.AuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@ExperimentalCoroutinesApi
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : AuthRepository {
    override fun isUserAuthenticatedInFirebase(): Boolean = auth.currentUser != null

    override suspend fun loginWithEmail(
        email: String,
        password: String
    ): Flow<ResultAuth<Boolean>> = flow {
        try {
            emit(ResultAuth.loading(false))
            auth.signInWithEmailAndPassword(email, password).await()
            emit(ResultAuth.success(true))
        } catch (e: Throwable) {
            if (e is FirebaseAuthWeakPasswordException) {
                emit(ResultAuth.failed("Contraseña incorecta", false))
            } else {
                emit(ResultAuth.failed("Correo o contraseña invalida", false))
            }
        }
    }

    override fun signOut(): Flow<ResultAuth<Boolean>> = flow {
        try {
            emit(ResultAuth.loading(false))
            auth.signOut()
            emit(ResultAuth.success(false))
        } catch (e: Exception) {
            emit(
                ResultAuth.failed(
                    e.message ?: "Tenemos incoveniente al cerrar la sesion. Intenta nuevamente",
                    false
                )
            )
        }
    }

    override suspend fun singInEmail(
        email: String,
        password: String
    ): Flow<ResultAuth<Boolean>> = flow {
        try {
            emit(ResultAuth.loading(false))
            auth.createUserWithEmailAndPassword(email, password).await()
            emit(ResultAuth.success(true))
        } catch (e: Throwable) {
            if (e is FirebaseAuthUserCollisionException) {
                emit(ResultAuth.collision("Esta cuenta ya existe", false))
            } else {
                emit(ResultAuth.failed("Error al crear tu cuenta", false))
            }
        }
    }

    override suspend fun singInAnonymously(): Flow<ResultAuth<Boolean>> = flow {
        try {
            emit(ResultAuth.loading(false))
            auth.signInAnonymously().await()
            emit(ResultAuth.success(false))
        } catch (e: Exception) {
            emit(ResultAuth.failed(e.message ?: "", false))
        }
    }

    override suspend fun sendRecoverPassword(
        email: String
    ): Flow<ResultAuth<Boolean>> = flow {
        try {
            emit(ResultAuth.loading(false))
            auth.sendPasswordResetEmail(email).await()
            emit(ResultAuth.success(true))
        } catch (e: Throwable) {
            emit(ResultAuth.failed("Error al enviar el mensaje", false))
        }
    }

    override fun getFirebaseAuthState(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener {
            offer(it.currentUser == null)
        }

        auth.addAuthStateListener(authStateListener)

        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }
}