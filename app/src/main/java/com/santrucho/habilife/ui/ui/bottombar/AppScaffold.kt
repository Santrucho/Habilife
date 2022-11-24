package com.santrucho.habilife.ui.ui.bottombar

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.santrucho.habilife.ui.navigation.Navigation

@Composable
fun AppScaffold(
    navController: NavController,
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        },
        scaffoldState = scaffoldState,
        ) {
        Navigation(navController = navController)
    }
}