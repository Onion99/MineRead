/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT License
 */

package org.onion.read.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import mineread.composeapp.generated.resources.Res
import mineread.composeapp.generated.resources.home
import mineread.composeapp.generated.resources.ic_help
import mineread.composeapp.generated.resources.setting
import mineread.composeapp.generated.resources.unknown
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource


sealed class Screen(
    val route: String,
    val iconRes: DrawableResource = Res.drawable.ic_help,
    val textRes: StringResource = Res.string.unknown,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    data object Home : Screen(
        route = "home", textRes = Res.string.home
    )

    data object Setting : Screen(
        route = "setting", textRes = Res.string.setting
    )

}