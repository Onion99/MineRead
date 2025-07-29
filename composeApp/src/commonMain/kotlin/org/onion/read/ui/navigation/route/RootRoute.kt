package org.onion.read.ui.navigation.route

import org.onion.read.ui.navigation.RoutePage


sealed interface RootRoute {
    data object Splash : RoutePage(name = "splash")
    data object MainContainer : RoutePage(name = "mainContainer")
}