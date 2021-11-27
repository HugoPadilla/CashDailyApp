package com.wenitech.cashdaily.framework.navigation

import androidx.annotation.DrawableRes
import com.wenitech.cashdaily.R


sealed class BottomNavDestinations(
    val route: String,
    val label: String,
    @DrawableRes val icon: Int
) {
    object Home : BottomNavDestinations("home", "Home", R.drawable.ic_inicio)
    object Clients : BottomNavDestinations("client", "Clientes", R.drawable.ic_clientes)
    object Box : BottomNavDestinations("box", "Caja", R.drawable.ic_box)
    object Report : BottomNavDestinations("report", "Informe", R.drawable.ic_informe)
}
