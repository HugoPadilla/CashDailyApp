package com.wenitech.cashdaily.domain.interaction.auth

import androidx.lifecycle.MutableLiveData
import com.wenitech.cashdaily.data.model.User
import com.wenitech.cashdaily.domain.repository.AuthenticationRepository
import com.wenitech.cashdaily.core.ResourceAuth
import javax.inject.Inject

class SignInUseCase @Inject constructor(var authenticationRepository: AuthenticationRepository) {

    fun execute(email: String, password: String, user: User): MutableLiveData<ResourceAuth<String>> {
        return authenticationRepository.singIn(email, password, user)
    }

}