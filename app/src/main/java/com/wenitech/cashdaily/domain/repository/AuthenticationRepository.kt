package com.wenitech.cashdaily.domain.repository

import androidx.lifecycle.MutableLiveData
import com.wenitech.cashdaily.data.model.User
import com.wenitech.cashdaily.core.ResourceAuth

interface AuthenticationRepository {
    fun logIn(email: String, password: String): MutableLiveData<ResourceAuth<String>>
    fun singIn(email: String, password: String, user: User): MutableLiveData<ResourceAuth<String>>
}