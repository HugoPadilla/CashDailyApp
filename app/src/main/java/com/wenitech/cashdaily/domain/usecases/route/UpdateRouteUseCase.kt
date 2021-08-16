package com.wenitech.cashdaily.domain.usecases.route

import com.wenitech.cashdaily.commons.Resource
import com.wenitech.cashdaily.domain.entities.Ruta
import com.wenitech.cashdaily.domain.repositories.RoutesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateRouteUseCase @Inject constructor(
    private val routesRepository: RoutesRepository
) {
    suspend operator fun invoke(idRoute: String, rutaUpdate: Ruta): Flow<Resource<String>> =
        routesRepository.updateNewRoute(idRoute, rutaUpdate)
}
