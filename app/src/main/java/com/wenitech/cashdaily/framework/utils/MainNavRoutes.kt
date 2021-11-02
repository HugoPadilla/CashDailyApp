package com.wenitech.cashdaily.framework.utils

import androidx.annotation.DrawableRes
import com.wenitech.cashdaily.R

sealed class MainNavRoutes(val route: String, val label: String, @DrawableRes val icon: Int) {
    object Home : MainNavRoutes("home", "Home", R.drawable.ic_inicio)
    object Clients : MainNavRoutes("clientes", "Clientes", R.drawable.ic_clientes)
    object Caja : MainNavRoutes("caja", "Caja", R.drawable.ic_box)
    object Informe : MainNavRoutes("informe", "Informe", R.drawable.ic_informe)
}
