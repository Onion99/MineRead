package org.onion.read.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.onion.theme.helper.verticalSafePadding
import com.onion.theme.state.AdaptiveLayoutType
import mineread.composeapp.generated.resources.Res
import mineread.composeapp.generated.resources.dark_theme
import mineread.composeapp.generated.resources.ic_moon
import mineread.composeapp.generated.resources.ic_sun
import mineread.composeapp.generated.resources.light_theme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.onion.read.ui.navigation.NavActions
import org.onion.read.ui.navigation.route.NAV_BOTTOM_ITEMS
import ui.theme.AppTheme

@Composable
fun MainScreen(rootNavActions: NavActions) {
    val mainFunNavController = rememberNavController()
    val mainFunNavActions = remember(mainFunNavController) {
        NavActions(mainFunNavController)
    }
    val navBackStackEntry by mainFunNavController.currentBackStackEntryAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    // ---- 当前路由所在的位置,堆栈层应该是 -> Splash,Main,Home,当前位置 ------
    val currentRoute = mainFunNavController.currentBackStack.value.getOrNull(3)?.destination?.route
    val selectedMainRoute = NAV_BOTTOM_ITEMS.forEach { routePage ->
        routePage.name == currentRoute
    }
    ModalNavigationDrawer(
        drawerContent = {
            ModalNavigationContent()
        },
        drawerState = drawerState,
        gesturesEnabled = false
    ){
        MainContent()
    }
}

val navigationDrawerMinWidth = 240.dp
val navigationDrawerMaxWidth = 360.dp

// ------------------------------------------------------------------------
// 抽屉式导航栏
// ------------------------------------------------------------------------
@Composable
fun ModalNavigationContent(){
    Column(
        modifier = Modifier.fillMaxHeight().padding(AppTheme.spacing.s200)
            .clip(AppTheme.shape.r500)
            .widthIn(min = navigationDrawerMinWidth, max = navigationDrawerMaxWidth)
            .border(AppTheme.size.borderWidth, AppTheme.colors.border, AppTheme.shape.r500)
            .background(AppTheme.colors.surfaceContainer)
            .padding(horizontal = AppTheme.spacing.s400, vertical = AppTheme.spacing.s450)
    ){

    }
}

// ------------------------------------------------------------------------
// 主导航栏
// ------------------------------------------------------------------------
@Composable
fun MainContent(){
    Column(Modifier.fillMaxSize()) {
        Row(modifier = Modifier.weight(1f)) {
            AnimatedVisibility(
                visible = AppTheme.adaptiveLayoutType == AdaptiveLayoutType.Medium || AppTheme.adaptiveLayoutType == AdaptiveLayoutType.Expanded
            ) {
                SlideNavigationBar(
                    modifier = Modifier.fillMaxHeight().padding(start = AppTheme.spacing.s300)
                        .padding(verticalSafePadding()),
                    onThemeChanged = {}
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                }
            }
        }
        AnimatedVisibility(visible = AppTheme.adaptiveLayoutType == AdaptiveLayoutType.Compact) {
            BottomNavigationBar(){}
        }
    }
}

@Composable
fun SlideNavigationBar(modifier: Modifier,onThemeChanged: () -> Unit){
    Column(
        modifier
            .border(AppTheme.size.borderWidth, AppTheme.colors.border, CircleShape)
            .background(AppTheme.colors.surfaceContainer, CircleShape).widthIn(min = 80.dp)
            .padding(vertical = AppTheme.spacing.s400).selectableGroup(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.s200)
    ) {
        Spacer(Modifier.height(AppTheme.spacing.s300))
        Column(
            modifier = Modifier.fillMaxHeight().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppTheme.spacing.s200)
        ) {
            NAV_BOTTOM_ITEMS.forEach { routePage ->
                NavigationRailItem(selected = false, onClick = {
                    }, icon = {
                        Icon(
                            imageVector = vectorResource(routePage.iconRes),
                            contentDescription = stringResource(routePage.textRes)
                        )
                    }, colors = navigationRailItemColors()
                )
            }

            Spacer(modifier = Modifier.weight(1f).heightIn(min = AppTheme.spacing.s400))
            val isDark by AppTheme.isDark
            NavigationRailItem(selected = false, onClick = {
                onThemeChanged()
            }, icon = {
                Icon(
                    imageVector = vectorResource(if (isDark) Res.drawable.ic_sun else Res.drawable.ic_moon),
                    contentDescription = stringResource(if (isDark) Res.string.light_theme else Res.string.dark_theme),
                    tint = AppTheme.colors.onSurfaceVariant
                )
            })
        }
    }
}
@Composable
fun BottomNavigationBar(onThemeChanged: () -> Unit){
    NavigationBar(modifier = Modifier.fillMaxWidth(), containerColor = AppTheme.colors.surface) {
        NAV_BOTTOM_ITEMS.forEach { routePage ->
            val isSelected = true
            NavigationRailItem(
                selected = false, onClick = {
                }, icon = {
                    Icon(
                        imageVector = vectorResource(routePage.iconRes),
                        contentDescription = stringResource(routePage.textRes)
                    )
                }, label = {
                    Text(
                        text = stringResource(routePage.textRes),
                        style = if (isSelected) AppTheme.typography.labelMedium else AppTheme.typography.labelSmall
                    )
                }, colors = navigationRailItemColors()
            )
        }
    }
}

@Composable
private fun navigationRailItemColors() = NavigationRailItemDefaults.colors(
    selectedIconColor = AppTheme.colors.onPrimaryContainer,
    selectedTextColor = AppTheme.colors.onSurfaceContainer,
    indicatorColor = AppTheme.colors.primaryContainer,
    unselectedIconColor = AppTheme.colors.onSurfaceVariant,
    unselectedTextColor = AppTheme.colors.onSurfaceVariant
)