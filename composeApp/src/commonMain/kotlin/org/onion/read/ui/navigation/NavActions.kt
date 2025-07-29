/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT License
 */

package org.onion.read.ui.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

class NavActions(private val navController: NavHostController) {

    fun navigationTo(destination: Screen) {
        navController.navigate(destination.route)
    }

    fun navigationToRoute(route: String) {
        navController.navigate(route)
    }

    fun back() {
        navController.popBackStack()
    }

    fun popAndNavigation(destination: Screen) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().route ?: MainFlow.Home.route) {
                this.inclusive = true
            }
        }
    }
}


