package org.onion.read

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.onion.read.ui.navigation.NavActions
import org.onion.read.ui.navigation.route.RootRoute
import org.onion.read.ui.screen.MainScreen
import org.onion.read.ui.screen.SplashScreen
import ui.theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme {
        val rootNavController = rememberNavController()
        val rootNavActions = remember(rootNavController) {
            NavActions(rootNavController)
        }
        NavHost(
            navController = rootNavController,
            startDestination = RootRoute.Splash.name
        ) {
            // ---- 开屏页,CPM启动会有一阵子白屏,还是依据具体平台定制化比较合理 ------
            composable(RootRoute.Splash.name) {
                SplashScreen(startMainFlow = { rootNavActions.popAndNavigation(RootRoute.MainContainer) })
            }
            composable(RootRoute.MainContainer.name) {
                MainScreen(rootNavActions)
            }
        }
    }
}