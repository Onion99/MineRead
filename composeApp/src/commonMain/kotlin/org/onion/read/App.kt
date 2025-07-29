package org.onion.read

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import mineread.composeapp.generated.resources.Res
import mineread.composeapp.generated.resources.compose_multiplatform
import org.onion.read.ui.navigation.NavActions
import org.onion.read.ui.navigation.RootFlow
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
            startDestination = RootFlow.MainContainer.route
        ) {
            composable(RootFlow.Splash.route) {
                SplashScreen(startMainFlow = { rootNavActions.popAndNavigation(RootFlow.MainContainer) })
            }
            composable(RootFlow.MainContainer.route) {
                MainScreen()
            }
        }
    }
}