package com.wenitech.cashdaily.framework.features.client.listClient

import com.wenitech.cashdaily.domain.entities.Client

data class ClientState(
    val listClient: List<Client> = listOf(),
    val loading: Boolean = false,
    val errorMessage: String? = null,
)


