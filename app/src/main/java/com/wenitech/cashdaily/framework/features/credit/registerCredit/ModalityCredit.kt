package com.wenitech.cashdaily.framework.features.credit.registerCredit

enum class ModalityOptions {
    MODALIDAD_DIARIO,
    MODALIDAD_SEMANAL,
    MODALIDAD_QINCENAL,
    MODALIDAD_MENSUAL,
}

sealed class ModalityCredit(
    val modalityOption: ModalityOptions, val modalityLabel: String
) {
    object Diario: ModalityCredit(ModalityOptions.MODALIDAD_DIARIO, "Diario")
    object Semanal: ModalityCredit(ModalityOptions.MODALIDAD_SEMANAL, "Semanal")
    object Quincenal: ModalityCredit(ModalityOptions.MODALIDAD_QINCENAL, "Quincenal")
    object Mensual: ModalityCredit(ModalityOptions.MODALIDAD_MENSUAL, "Mensual")
}