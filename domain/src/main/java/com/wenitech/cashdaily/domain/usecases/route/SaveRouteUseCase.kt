package com.wenitech.cashdaily.domain.usecases.route

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.Ruta
import com.wenitech.cashdaily.domain.repositories.RoutesRepository
import kotlinx.coroutines.flow.Flow

class SaveRouteUseCase(
    private val routesRepository: RoutesRepository
) {
    suspend operator fun invoke(newRoute: Ruta): Flow<Response<String>> =
        routesRepository.saveNewRoute(newRoute)
}