package com.wenitech.cashdaily.framework.features.client.newClient

enum class TypeDialog {
    Loading,
    Success,
    Error,
    None
}

data class RegisterClientUiState(
    val showDialog: TypeDialog = TypeDialog.None,
    val registeredCustomerId: String = "",
    val fullName: String = "",
    val idNumber: String = "",
    val gender: String = "",
    val phoneNumber: String = "",
    val city: String = "",
    val direction: String = "",
    val buttonEnabled: Boolean = false
)