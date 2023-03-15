package com.santrucho.habilife.ui.ui.bottombar

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.santrucho.habilife.R

//Set the bottom bar to navigate between screens
@Composable
fun BottomBar(
    items:List<BottomNavScreen>,
    navController: NavController,
    onClick:(BottomNavScreen) -> Unit) {

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        contentColor = MaterialTheme.colors.primaryVariant
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 10.sp
                    )
                },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.primaryVariant,
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    onClick(item)

                }
            )
        }
    }
}

