package com.wenitech.cashdaily.di

import com.wenitech.cashdaily.data.remoteDataSource.RemoteDataSource
import com.wenitech.cashdaily.data.remoteDataSource.RemoteDataSourceImpl
import com.wenitech.cashdaily.data.repositories.AuthRepositoryImpl
import com.wenitech.cashdaily.data.repositories.DataRepositoryImp
import com.wenitech.cashdaily.data.repositories.RouteRepositoryImpl
import com.wenitech.cashdaily.domain.repositories.AuthRepository
import com.wenitech.cashdaily.domain.repositories.DataRepository
import com.wenitech.cashdaily.domain.repositories.RoutesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityModule {
    // Binds interfaces con implementation
    @Binds
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindDataRepository(dataRepositoryImp: DataRepositoryImp): DataRepository

    @Binds
    abstract fun bindRouteRepository(routeRepositoryImpl: RouteRepositoryImpl): RoutesRepository

}