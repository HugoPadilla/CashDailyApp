package com.wenitech.cashdaily.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.wenitech.cashdaily.data.entities.toUserDomain
import com.wenitech.cashdaily.data.remoteDataSource.UserRemoteDataSource
import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.common.ResultAuth
import com.wenitech.cashdaily.domain.entities.User
import com.wenitech.cashdaily.domain.repositories.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@ExperimentalCoroutinesApi
class LoginRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val userRemoteDataSource: UserRemoteDataSource
) : LoginRepository {
    override fun isUserAuthenticated(): Boolean = auth.currentUser != null

    override fun loginWithEmail(
        email: String, password: String
    ): Flow<ResultAuth<Boolean>> = flow {
        try {
            emit(ResultAuth.Loading)
            auth.signInWithEmailAndPassword(email, password).await()
            emit(ResultAuth.Success(true))
        } catch (e: Throwable) {
            when (e) {
                is FirebaseAuthWeakPasswordException -> {
                    emit(ResultAuth.Failed("Contraseña incorecta", e))
                }
                else -> {
                    emit(ResultAuth.Failed("Correo o contraseña invalida", e))
                }
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun signOut(): Flow<ResultAuth<Boolean>> = flow {
        try {
            emit(ResultAuth.Loading)
            auth.signOut()
            emit(ResultAuth.Success(true))
        } catch (e: Exception) {
            emit(
                ResultAuth.Failed(
                    e.message ?: "Tenemos incoveniente al cerrar la sesion. Intenta nuevamente",
                    e
                )
            )
        }
    }.flowOn(Dispatchers.IO)

    override fun authState(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener {
            offer(it.currentUser != null)
        }

        auth.addAuthStateListener(authStateListener)

        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.flowOn(Dispatchers.IO)

    override fun getUserProfile(): Flow<Response<User>> {
        return userRemoteDataSource.readUserProfile().transform { response ->
            when (response) {
                is Response.Error -> return@transform emit(
                    Response.Error(
                        response.throwable,
                        response.msg
                    )
                )
                is Response.Loading -> return@transform emit(Response.Loading)
                is Response.Success -> return@transform emit(Response.Success(response.data.toUserDomain()))
            }
        }.flowOn(Dispatchers.IO)
    }
}