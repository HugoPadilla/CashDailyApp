package com.wenitech.cashdaily.data.repositories

import com.wenitech.cashdaily.data.entities.CreditModel
import com.wenitech.cashdaily.data.entities.QuotaModel
import com.wenitech.cashdaily.data.entities.toData
import com.wenitech.cashdaily.data.entities.toDomain
import com.wenitech.cashdaily.data.remoteDataSource.CustomerCreditRemoteDataSource
import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.Credit
import com.wenitech.cashdaily.domain.entities.Quota
import com.wenitech.cashdaily.domain.repositories.CreditRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class CreditRepositoryImpl(private val remoteDataSourceCustomer: CustomerCreditRemoteDataSource) :
    CreditRepository {
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
                is Response.Success -> return@transform emit(Response.Success(it.data.map { it.toDomain() }))
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
                is Response.Success -> return@transform emit(Response.Success(it.data.toDomain()))
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
                is Response.Success -> return@transform emit(Response.Success(it.data.map { it.toDomain() }))
            }
        }
    }

    override suspend fun saveNewQuota(
        idClient: String,
        idCredit: String,
        newQuota: Quota,
    ): Flow<Response<String>> {
        return remoteDataSourceCustomer.saveNewQuota(idClient, idCredit, QuotaModel(value = newQuota.value))
    }
}