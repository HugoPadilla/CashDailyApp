package com.wenitech.cashdaily.domain.entities

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.wenitech.cashdaily.domain.constant.PaymentMethodEnum

class Credit(
    @DocumentId
    val id: String? = null,
    var timestampCreation: Timestamp? = null,
    var timestampNextPayment: Timestamp? = null,
    var paymentMethod: String = PaymentMethodEnum.Daily.name,
    var creditDebt: Double = 0.0,
    var creditQuotaValue: Double = 0.00,
    var creditTotal: Double = 0.0,
    var creditValue: Double = 0.00,
    var percentage: Int = 0,
    var amountFees: Int = 0,
    var isChargedOnSaturday: Boolean = false,
    var isChargedOnSunday: Boolean = false,
)