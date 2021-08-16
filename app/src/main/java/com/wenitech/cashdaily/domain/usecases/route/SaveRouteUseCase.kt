package com.wenitech.cashdaily.domain.usecases.route

import com.wenitech.cashdaily.commons.Resource
import com.wenitech.cashdaily.domain.entities.Ruta
import com.wenitech.cashdaily.domain.repositories.RoutesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveRouteUseCase @Inject constructor(
    private val routesRepository: RoutesRepository
) {
    suspend operator fun invoke(newRoute: Ruta): Flow<Resource<String>> =
        routesRepository.saveNewRoute(newRoute)
}