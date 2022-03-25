package com.wenitech.cashdaily.data.di

import com.wenitech.cashdaily.data.remoteDataSource.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {
    @Binds
    abstract fun bindBoxRemoteDataSource(boxRemoteDataSourceImpl: BoxRemoteDataSourceImpl): BoxRemoteDataSource

    @Binds
    abstract fun bindClientRemoteDataSource(clientRemoteDataSourceImpl: ClientRemoteDataSourceImpl): ClientRemoteDataSource

    @Binds
    abstract fun bindCustomerCreditRemoteDataSource(customerCreditRemoteDataSourceImpl: CustomerCreditRemoteDataSourceImpl): CustomerCreditRemoteDataSource

    @Binds
    abstract fun bindGeneralReportRemoteDataSource(generalReportRemoteDataSourceImpl: GeneralReportRemoteDataSourceImp): GeneralReportRemoteDataSource

    @Binds
    abstract fun bindUserRemoteDataSource(userRemoteDataSource: UserRemoteDataSourceImpl): UserRemoteDataSource
}
