package com.wenitech.cashdaily.data.entities

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.wenitech.cashdaily.domain.constant.PaymentMethodEnum
import com.wenitech.cashdaily.domain.entities.Credit

class CreditModel(
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

fun CreditModel.toDomain() = Credit(
    id = id,
    dateCreation = timestampCreation?.toDate(),
    dateNextPayment = timestampNextPayment?.toDate(),
    paymentMethod = paymentMethod,
    creditDebt = creditDebt,
    creditQuotaValue = creditQuotaValue,
    creditTotal = creditTotal,
    creditValue = creditValue,
    percentage = percentage,
    amountFees = amountFees,
    isChargedOnSaturday = isChargedOnSaturday,
    isChargedOnSunday = isChargedOnSunday,
)

fun CreditModel.toData(credit: Credit) = CreditModel(
    credit.id,
    Timestamp(credit.dateCreation!!.time, 0),
    Timestamp(credit.dateNextPayment!!.time, 0),
    credit.paymentMethod,
    credit.creditDebt,
    credit.creditQuotaValue,
    credit.creditTotal,
    credit.creditValue,
    credit.percentage,
    credit.amountFees,
    credit.isChargedOnSaturday,
    credit.isChargedOnSunday
)