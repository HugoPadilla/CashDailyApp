package com.wenitech.cashdaily.data.remoteDataSource

import com.google.firebase.firestore.DocumentSnapshot
import com.wenitech.cashdaily.data.entities.UserDto

interface UserRemoteDataSource {

    suspend fun createUserProfile(userDto: UserDto)

    suspend fun getUserProfile(): DocumentSnapshot?

}