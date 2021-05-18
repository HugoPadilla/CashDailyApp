package com.wenitech.cashdaily.di

import com.wenitech.cashdaily.data.firebase.Authentication
import com.wenitech.cashdaily.data.firebase.AuthenticationImpl
import com.wenitech.cashdaily.data.firebase.Firestore
import com.wenitech.cashdaily.data.firebase.FirestoreImpl
import com.wenitech.cashdaily.data.repository.AuthenticationRepositoryImp
import com.wenitech.cashdaily.domain.repository.FirestoreRepository
import com.wenitech.cashdaily.data.repository.FirestoreRepositoryImp
import com.wenitech.cashdaily.domain.repository.AuthenticationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityModule {
    // Binds interfaces con implementaciones

    @Binds
    abstract fun bindAuthenticationRepositoryImpl(authenticationRepositoryImp: AuthenticationRepositoryImp) : AuthenticationRepository

    @Binds
    abstract fun bindAuthenticationImpl(authenticationImpl: AuthenticationImpl) : Authentication

    @Binds
    abstract fun bindFirestoreRepositoryImpl(firestoreRepositoryImp: FirestoreRepositoryImp) : FirestoreRepository

    @Binds
    abstract fun bindFirestoreDataBaseImpl(firestoreImpl: FirestoreImpl) : Firestore
}