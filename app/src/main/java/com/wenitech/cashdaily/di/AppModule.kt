package com.wenitech.cashdaily.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.wenitech.cashdaily.commons.NetWorkStatus
import com.wenitech.cashdaily.data.databaselocal.Database
import com.wenitech.cashdaily.data.remoteDataSource.*
import com.wenitech.cashdaily.data.remoteDataSource.routes.Constant
import com.wenitech.cashdaily.data.repositories.*
import com.wenitech.cashdaily.domain.repositories.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /**
     * Provee una instancia de Firebase authentication
     */
    @Provides
    fun providerAuthFirebase(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    /**
     * Provee una instancia Firebase firestore
     */
    @Provides
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    /**
     * Constantes: Nombres de las Rutas de colecciones y documento de Firestore
     */
    @Singleton
    @Provides
    fun provideConstantFirestore(firestore: FirebaseFirestore, auth: FirebaseAuth): Constant {
        return Constant(firestore, auth)
    }

    /**
     * Implementacion de acceso a la base de datos de Firestore
     */
    @Provides
    fun provideRemoteDataSource(
        firestore: FirebaseFirestore,
        constant: Constant
    ): ClientRemoteDataSource {
        return ClientRemoteDataSourceImpl(firestore, constant)
    }

    @Provides
    fun provideBoxRemoteDataSource(db: FirebaseFirestore, constant: Constant): BoxRemoteDataSource {
        return BoxRemoteDataSourceImpl(db, constant)
    }

    @Provides
    fun provideUserRemoteDataSource(
        db: FirebaseFirestore,
        constant: Constant
    ): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(db, constant)
    }

    @Provides
    fun provideCreditRemoteDataSource(
        db: FirebaseFirestore,
        constant: Constant
    ): CustomerCreditRemoteDataSource {
        return CustomerCreditRemoteDataSourceImpl(db, constant)
    }

    /**
     * Provee instancia de Repositorio de authenticacion
     */
    @Singleton
    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
    ): AuthRepository {
        return AuthRepositoryImpl(auth)
    }

    @Singleton
    @Provides
    fun provideUserRepository(userRemoteDataSource: UserRemoteDataSource): UserRepository {
        return UserRepositoryImpl(userRemoteDataSource)
    }

    /**
     * Provee instancia de Repositorio de acceso a los datos
     */
    @Singleton
    @Provides
    fun provideDataRepository(
        clientRemoteDataSource: ClientRemoteDataSource,
    ): DataRepository {
        return DataRepositoryImp(clientRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideBoxRepository(boxRemoteDataSource: BoxRemoteDataSource): BoxRepository {
        return BoxRepositoryImpl(boxRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideCreditRepository(customerCreditRemoteDataSource: CustomerCreditRemoteDataSource): CreditRepository {
        return CreditRepositoryImpl(customerCreditRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideRouteRepository(
        firestore: FirebaseFirestore,
        constant: Constant
    ): RoutesRepository {
        return RouteRepositoryImpl(firestore, constant)
    }

    /**
     * Provee utilidad de estado de red
     */
    @Singleton
    @Provides
    fun providerNetWorkStatus(): NetWorkStatus {
        return NetWorkStatus()
    }

    /**
     *  Provee Room database
     */
    @Singleton
    @Provides
    fun providerRoomInstance(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            Database::class.java,
            "table_client"
        ).build()

    /**
     * Provee Objeto Client DAO
     */
    @Singleton
    @Provides
    fun provideClientDao(db: Database) = db.clientDao()

}