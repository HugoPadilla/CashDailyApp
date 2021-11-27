package com.wenitech.cashdaily.framework.features.client.customerCredit

import com.wenitech.cashdaily.domain.entities.Client
import com.wenitech.cashdaily.domain.entities.Credit
import com.wenitech.cashdaily.domain.entities.Quota

data class CustomerCreditState(
    val loading: Boolean = false,
    val client: Client = Client(),
    val credit: Credit = Credit(),
    val listQuota: List<Quota> = listOf(),
)
