package com.wenitech.cashdaily.framework.features.authentication

import androidx.lifecycle.ViewModel
import com.wenitech.cashdaily.domain.usecases.auth.IsUserAuthenticatedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartAuthScreenViewModel @Inject constructor(
    private val isUserAuthenticatedUseCase: IsUserAuthenticatedUseCase,
): ViewModel() {

    val isUserAuthenticated get() = isUserAuthenticatedUseCase()

}