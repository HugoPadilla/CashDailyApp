package com.wenitech.cashdaily.framework.features.client.profileClient

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class ProfileClientViewModel @Inject constructor(
    private val auth: FirebaseAuth,
) : ViewModel() {
    // TODO: Implement the ViewModel
}