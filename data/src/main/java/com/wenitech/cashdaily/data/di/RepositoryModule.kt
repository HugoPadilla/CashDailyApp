package com.wenitech.cashdaily.data.di

import com.wenitech.cashdaily.data.repositories.*
import com.wenitech.cashdaily.domain.repositories.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindBoxRepository(boxRepositoryImpl: BoxRepositoryImpl): BoxRepository

    @Binds
    abstract fun bindCreditRepository(creditRepositoryImpl: CreditRepositoryImpl): CreditRepository

    @Binds
    abstract fun bindDataRepository(dataRepositoryImp: DataRepositoryImp): DataRepository

    @Binds
    abstract fun bindGeneralReportRepository(generalReportRepositoryImpl: GeneralReportRepositoryImpl): GeneralReportRepository

    @Binds
    abstract fun bindRoutesRepository(routeRepositoryImpl: RouteRepositoryImpl): RoutesRepository

    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}