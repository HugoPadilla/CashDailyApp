package com.wenitech.cashdaily.data.repositories

import com.google.firebase.firestore.FirebaseFirestore
import com.wenitech.cashdaily.data.entities.*
import com.wenitech.cashdaily.data.remoteDataSource.CustomerCreditRemoteDataSource
import com.wenitech.cashdaily.data.remoteDataSource.routes.Constant
import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.Credit
import com.wenitech.cashdaily.domain.entities.Quota
import com.wenitech.cashdaily.domain.repositories.CreditRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CreditRepositoryImpl @Inject constructor(
    private val remoteDataSourceCustomer: CustomerCreditRemoteDataSource,
    private val db: FirebaseFirestore,
    private val constant: Constant
) : CreditRepository {
    override suspend fun deleteCustomerCredit(
        idClient: String,
        idCredit: String
    ): Flow<Response<Void?>> = flow {
        emit(Response.Loading)

        //Document ref
        val refBox = constant.getDocumentBox()
        val refCredit = constant.getDocumentCredit(idClient, idCredit)

        db.runTransaction { transition ->

            // 1- Read document
            val boxServer = transition.get(refBox).toObject(BoxDto::class.java)
            val creditServer = transition.get(refCredit).toObject(CreditModel::class.java)

            boxServer?.let {
                transition.update(
                    refBox,
                    BoxDto::totalCash.name,
                    boxServer.totalCash - (creditServer?.creditValue
                        ?: 0.0)
                )
            }

        }.await()

        emit(Response.Success(null))

    }.catch {
        emit(Response.Error(it))
    }.flowOn(Dispatchers.IO)

    override suspend fun getRecentCredits(
        uid: String,
        idClient: String
    ): Flow<Response<List<Credit>>> {
        return remoteDataSourceCustomer.getRecentCredits(uid, idClient).transform {
            when (it) {
                is Response.Error -> return@transform emit(
                    Response.Error(
                        it.throwable,
                        it.msg
                    )
                )
                is Response.Loading -> return@transform emit(Response.Loading)
                is Response.Success -> return@transform emit(Response.Success(it.data.map { it.toBox() }))
            }
        }
    }

    override suspend fun saveNewCredit(
        idClient: String,
        newCredit: Credit
    ): Flow<Response<String>> {
        return remoteDataSourceCustomer.saveNewCredit(idClient, CreditModel().toData(newCredit))
    }

    override suspend fun getCreditClient(
        idClient: String,
        idCredit: String
    ): Flow<Response<Credit>> {
        return remoteDataSourceCustomer.getCreditClient(idClient, idCredit).transform {

            when (it) {
                is Response.Error -> return@transform emit(
                    Response.Error(
                        it.throwable,
                        it.msg
                    )
                )
                is Response.Loading -> return@transform emit(Response.Loading)
                is Response.Success -> return@transform emit(Response.Success(it.data.toBox()))
            }
        }
    }

    override suspend fun getQuotaCredit(
        idClient: String,
        idCredit: String
    ): Flow<Response<List<Quota>>> {
        return remoteDataSourceCustomer.getQuotaCredit(idClient, idCredit).transform {
            when (it) {
                is Response.Error -> return@transform emit(
                    Response.Error(
                        it.throwable,
                        it.msg
                    )
                )
                is Response.Loading -> return@transform emit(Response.Loading)
                is Response.Success -> return@transform emit(Response.Success(it.data.map { it.toQuota() }))
            }
        }
    }

    override suspend fun saveNewQuota(
        idClient: String,
        idCredit: String,
        newQuota: Quota,
    ): Flow<Response<String>> {
        return remoteDataSourceCustomer.saveNewQuota(
            idClient,
            idCredit,
            QuotaDto(value = newQuota.value)
        )
    }
}