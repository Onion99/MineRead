package org.onion.read.ui.navigation.route

import mineread.composeapp.generated.resources.Res
import mineread.composeapp.generated.resources.home
import mineread.composeapp.generated.resources.ic_home
import org.onion.read.ui.navigation.RoutePage


sealed interface MainRoute {
    data object Home : RoutePage(name = "home",Res.drawable.ic_home, Res.string.home)
    data object Setting : RoutePage(name = "setting",Res.drawable.ic_home, Res.string.home)
    data object Mine : RoutePage(name = "mine",Res.drawable.ic_home, Res.string.home)
}
val NAV_BOTTOM_ITEMS = listOf(
    MainRoute.Home,
    MainRoute.Setting,
    MainRoute.Mine
)