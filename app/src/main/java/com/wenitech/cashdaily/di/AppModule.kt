package com.wenitech.cashdaily.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.wenitech.cashdaily.commons.NetWorkStatus
import com.wenitech.cashdaily.data.databaselocal.Database
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