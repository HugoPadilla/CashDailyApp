package com.wenitech.cashdaily.domain.usecases.route

import com.wenitech.cashdaily.domain.common.Response
import com.wenitech.cashdaily.domain.entities.Ruta
import com.wenitech.cashdaily.domain.repositories.RoutesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRoutesUseCase @Inject constructor(
    private val routesRepository: RoutesRepository
) {
    suspend operator fun invoke(): Flow<Response<List<Ruta>>> =
        routesRepository.getRoutes()
}