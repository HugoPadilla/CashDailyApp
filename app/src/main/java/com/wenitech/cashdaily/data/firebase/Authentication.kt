package com.wenitech.cashdaily.data.firebase

import androidx.lifecycle.MutableLiveData
import com.wenitech.cashdaily.core.ResourceAuth
import com.wenitech.cashdaily.data.model.User

interface Authentication {
    /**
     * Login in dadaBase
     *
     * @param email
     * @param password
     */
    fun LoginFirebase(email: String, password: String): MutableLiveData<ResourceAuth<String>>

    /**
     * New account for user with Email and Password
     *
     * @param email
     * @param password
     * @param user
     */
    fun SignInFirebase(email: String, password: String, user: User): MutableLiveData<ResourceAuth<String>>

    /**
     * Reset account for new password.
     * Send  the email at user for recovery account
     *
     * @param email
     * @param password
     */
    fun ResetPasswordFirebase(email: String?, password: String?): MutableLiveData<ResourceAuth<String>>
}