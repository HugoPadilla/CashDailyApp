package com.wenitech.cashdaily.domain.repositories

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.Ruta
import kotlinx.coroutines.flow.Flow

interface RoutesRepository {

    suspend fun getRoutes(): Flow<Response<List<Ruta>>>

    suspend fun saveNewRoute(newRoute: Ruta): Flow<Response<String>>

    suspend fun updateNewRoute(idRoute: String, updateRoute: Ruta): Flow<Response<String>>

    suspend fun removeNewRoute(idRoute: String): Flow<Response<String>>

}