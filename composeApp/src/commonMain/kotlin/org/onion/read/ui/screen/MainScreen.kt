package org.onion.read.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import org.onion.read.ui.navigation.NavActions

@Composable
fun MainScreen() {
    val mainFunNavController = rememberNavController()
    val mainFunNavActions = remember(mainFunNavController) {
        NavActions(mainFunNavController)
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent = {
            ModalNavigationDrawerContent()
        },
        drawerState = drawerState,
        gesturesEnabled = false
    ){
        MainContent()
    }
}

@Composable
fun ModalNavigationDrawerContent(){

}

@Composable
fun MainContent(){
    Column(Modifier.fillMaxSize()) {

    }
}