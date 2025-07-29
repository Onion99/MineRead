/*
 * Copyright 2024 The ZZZ Archive Open Source Project by mrfatworm
 * License: MIT License
 */

package org.onion.read.ui.navigation

import mineread.composeapp.generated.resources.Res
import mineread.composeapp.generated.resources.home
import mineread.composeapp.generated.resources.ic_help
import mineread.composeapp.generated.resources.ic_home
import mineread.composeapp.generated.resources.ic_setting
import mineread.composeapp.generated.resources.setting
import mineread.composeapp.generated.resources.unknown
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

sealed class MainFlow(
    val route: String,
    val startScreen: Screen,
    val iconRes: DrawableResource = Res.drawable.ic_help,
    val textRes: StringResource = Res.string.unknown
) {
    data object Home : MainFlow(
        route = "home_flow",
        startScreen = Screen.Home,
        iconRes = Res.drawable.ic_home,
        textRes = Res.string.home
    )


    data object Setting : MainFlow(
        route = "setting_flow",
        startScreen = Screen.Setting,
        iconRes = Res.drawable.ic_setting,
        textRes = Res.string.setting
    )
}
