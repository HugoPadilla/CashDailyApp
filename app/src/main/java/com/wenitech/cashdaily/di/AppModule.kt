package com.wenitech.cashdaily.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.wenitech.cashdaily.commons.NetWorkStatus
import com.wenitech.cashdaily.data.databaselocal.Database
import com.wenitech.cashdaily.data.remoteDataSource.RemoteDataSource
import com.wenitech.cashdaily.data.remoteDataSource.RemoteDataSourceImpl
import com.wenitech.cashdaily.data.remoteDataSource.routes.Constant
import com.wenitech.cashdaily.data.repositories.AuthRepositoryImpl
import com.wenitech.cashdaily.data.repositories.DataRepositoryImp
import com.wenitech.cashdaily.data.repositories.RouteRepositoryImpl
import com.wenitech.cashdaily.domain.repositories.AuthRepository
import com.wenitech.cashdaily.domain.repositories.DataRepository
import com.wenitech.cashdaily.domain.repositories.RoutesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
    ): RemoteDataSource {
        return RemoteDataSourceImpl(firestore, constant);
    }

    /**
     * Provee instancia de Repositorio de authenticacion
     */
    @Singleton
    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
        remoteDataSource: RemoteDataSource
    ): AuthRepository {
        return AuthRepositoryImpl(auth, remoteDataSource)
    }

    /**
     * Provee instancia de Repositorio de acceso a los datos
     */
    @Singleton
    @Provides
    fun provideDataRepository(
        remoteDataSource: RemoteDataSource,
        auth: AuthRepository
    ): DataRepository {
        return DataRepositoryImp(remoteDataSource, auth)
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