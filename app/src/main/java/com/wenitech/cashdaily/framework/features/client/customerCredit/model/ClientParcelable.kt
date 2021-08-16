package com.wenitech.cashdaily.framework.features.client.customerCredit.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Objeto de dato para enviar argumentos pesonalisados con Navigation
 *
 * @param [id] id del documento en la base de datos
 * @param [idCredit] referencia del documento credito en la base de datos remota
 */
@Parcelize
data class ClientParcelable(
    val id: String,
    var isCreditActive: Boolean,
    var idCredit: String? = null,
    var fullName: String,
    var identificationClient: String,
    var gender: String,
    var phoneNumber: String,
    var city: String,
    var direction: String
) : Parcelable