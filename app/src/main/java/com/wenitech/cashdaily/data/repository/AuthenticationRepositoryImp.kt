package com.wenitech.cashdaily.data.repository

import androidx.lifecycle.MutableLiveData
import com.wenitech.cashdaily.data.model.User
import com.wenitech.cashdaily.domain.repository.AuthenticationRepository
import com.wenitech.cashdaily.core.ResourceAuth
import com.wenitech.cashdaily.data.firebase.Authentication
import javax.inject.Inject

class AuthenticationRepositoryImp @Inject constructor(
        private val authentication: Authentication,
) : AuthenticationRepository {

    // Todo: Metodo Public
    override fun logIn(email: String, password: String): MutableLiveData<ResourceAuth<String>> {
        return authentication.LoginFirebase(email, password)
    }

    override fun singIn(email: String, password: String, user: User): MutableLiveData<ResourceAuth<String>> {
        return authentication.SignInFirebase(email, password, user)
    }


}