package com.wenitech.cashdaily.domain.usecases.route

import com.wenitech.cashdaily.domain.entities.Ruta
import com.wenitech.cashdaily.domain.repositories.RoutesRepository
import kotlinx.coroutines.flow.Flow

class UpdateRouteUseCase(
    private val routesRepository: RoutesRepository
) {
    suspend operator fun invoke(
        idRoute: String,
        rutaUpdate: Ruta
    ): Flow<com.wenitech.cashdaily.domain.common.Response<String>> =
        routesRepository.updateNewRoute(idRoute, rutaUpdate)
}
