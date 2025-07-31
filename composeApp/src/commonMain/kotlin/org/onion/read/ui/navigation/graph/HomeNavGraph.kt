package org.onion.read.ui.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import org.onion.read.ui.navigation.NavActions
import org.onion.read.ui.navigation.route.DetailRoute
import org.onion.read.ui.navigation.route.MainRoute
import org.onion.read.ui.screen.homeScreen

fun NavGraphBuilder.homeNavGraph(navActions: NavActions) {
    navigation( DetailRoute.Home.name,MainRoute.HomeRoute.name){
        homeScreen()
    }
}