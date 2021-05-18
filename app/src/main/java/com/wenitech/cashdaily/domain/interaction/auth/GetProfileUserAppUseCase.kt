package com.wenitech.cashdaily.domain.interaction.auth

import com.wenitech.cashdaily.data.model.User
import com.wenitech.cashdaily.domain.repository.FirestoreRepository
import com.wenitech.cashdaily.core.ResourceAuth
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUserAppUseCase @Inject constructor(private val firestoreRepository: FirestoreRepository) {

    fun execute(): Flow<ResourceAuth<User>> {
        return firestoreRepository.getUserAppProfile()
    }
}