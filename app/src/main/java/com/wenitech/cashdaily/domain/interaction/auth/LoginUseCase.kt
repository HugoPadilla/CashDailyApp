package com.wenitech.cashdaily.domain.interaction.auth

import androidx.lifecycle.MutableLiveData
import com.wenitech.cashdaily.domain.repository.AuthenticationRepository
import com.wenitech.cashdaily.core.ResourceAuth
import javax.inject.Inject

class LoginUseCase @Inject constructor(var authenticationRepository: AuthenticationRepository) {

    fun execute(email: String, password: String): MutableLiveData<ResourceAuth<String>> {
        return authenticationRepository.logIn(email, password)
    }

}