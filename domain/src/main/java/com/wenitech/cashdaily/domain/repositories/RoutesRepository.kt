package com.wenitech.cashdaily.domain.repositories

import com.wenitech.cashdaily.domain.common.Resource
import com.wenitech.cashdaily.domain.entities.Ruta
import kotlinx.coroutines.flow.Flow

interface RoutesRepository {

    suspend fun getRoutes(): Flow<Resource<List<Ruta>>>

    suspend fun saveNewRoute(newRoute: Ruta): Flow<Resource<String>>

    suspend fun updateNewRoute(idRoute: String, updateRoute: Ruta): Flow<Resource<String>>

    suspend fun removeNewRoute(idRoute: String): Flow<Resource<String>>

}