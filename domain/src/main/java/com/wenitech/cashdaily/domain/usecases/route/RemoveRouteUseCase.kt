package com.wenitech.cashdaily.domain.usecases.route

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.repositories.RoutesRepository
import kotlinx.coroutines.flow.Flow

class RemoveRouteUseCase(
    private val routesRepository: RoutesRepository
) {
    suspend operator fun invoke(idRoute: String): Flow<Response<String>> =
        routesRepository.removeNewRoute(idRoute)
}