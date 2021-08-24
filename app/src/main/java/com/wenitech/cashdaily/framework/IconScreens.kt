package com.wenitech.cashdaily.framework

import androidx.annotation.DrawableRes
import com.wenitech.cashdaily.R

sealed class IconScreens(val route: String, val label: String, @DrawableRes val icon: Int) {
    object Home : IconScreens("home", "Home", R.drawable.ic_inicio)
    object Clients : IconScreens("clientes", "Clientes", R.drawable.ic_clientes)
    object Caja : IconScreens("caja", "Caja", R.drawable.ic_box)
    object Informe : IconScreens("informe", "Informe", R.drawable.ic_informe)
}
