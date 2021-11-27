package com.wenitech.cashdaily.framework.navigation

const val CLIENT_ROUTE = "client_route"
const val CREDIT_ROUTE = "credit_route"

sealed class ClientDestinations(val route: String) {
    object SelectClient: ClientDestinations("select_client")
    object RegisterCredit : ClientDestinations("register_credit")
    object CustomerCredit: ClientDestinations("customer_credit")
}