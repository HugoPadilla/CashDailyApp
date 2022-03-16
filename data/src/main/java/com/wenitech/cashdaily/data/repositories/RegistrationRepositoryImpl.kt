package com.wenitech.cashdaily.data.repositories

import com.google.firebase.auth.*
import com.wenitech.cashdaily.data.entities.UserModel
import com.wenitech.cashdaily.data.remoteDataSource.UserRemoteDataSource
import com.wenitech.cashdaily.domain.common.ResultAuth
import com.wenitech.cashdaily.domain.repositories.RegistrationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val userRemoteDataSource: UserRemoteDataSource
) : RegistrationRepository {
    override fun registerWithEmail(
        email: String, password: String
    ): Flow<ResultAuth<String>> = flow {
        try {
            emit(ResultAuth.Loading)
            val resultAuth = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = resultAuth.user
            userRemoteDataSource.createUserProfile(
                UserModel(
                    id = firebaseUser?.uid,
                    email = email
                )
            )
            emit(ResultAuth.Success(""))
        } catch (e: Exception) {
            when (e) {
                is FirebaseAuthInvalidCredentialsException -> emit(ResultAuth.Failed())
                is FirebaseAuthUserCollisionException -> emit(ResultAuth.Collision("Ya existe un usuario regsitrado con este correo electronico"))
                is FirebaseAuthWeakPasswordException -> emit(ResultAuth.Failed())
                else -> {emit(ResultAuth.Failed(e.message, e))}
            }
        }
    }.flowOn(Dispatchers.IO)

    override fun registerWithAnonymously(): Flow<ResultAuth<Boolean>> = flow {
        try {
            emit(ResultAuth.Loading)
            auth.signInAnonymously().await()
            emit(ResultAuth.Success(true))
        } catch (e: Exception) {
            emit(ResultAuth.Failed(e.message ?: "", e))
        }
    }.flowOn(Dispatchers.IO)

    override fun sendRecoveryPasswordMessageToEmail(
        email: String
    ): Flow<ResultAuth<Boolean>> = flow {
        try {
            emit(ResultAuth.Loading)
            auth.sendPasswordResetEmail(email).await()
            emit(ResultAuth.Success(true))
        } catch (e: Throwable) {
            when (e) {
                is FirebaseAuthEmailException -> emit(ResultAuth.Failed(e.message, e))
                else -> emit(
                    ResultAuth.Failed(
                        "No fue posible enviarte un corre electronico. Por favor, revisa tu conexion o intenta mas tarde",
                        e
                    )
                )
            }

        }
    }.flowOn(Dispatchers.IO)
}