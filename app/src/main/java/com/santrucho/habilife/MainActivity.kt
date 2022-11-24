package com.santrucho.habilife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.santrucho.habilife.ui.ui.bottombar.AppScaffold
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.theme.HabilifeTheme
import com.santrucho.habilife.ui.ui.login.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HabilifeTheme {
                // A surface container using the 'background' color from the theme
                LoginApplication()
            }
        }
    }
}

@Composable
fun LoginApplication() {
    val navController = rememberNavController()
    val navBarNavController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(
            route = Screen.LoginScreen.route,
            content = {
                LoginScreen(
                    navController = navController,
                )
            })
        composable(
            route = Screen.AppScaffold.route,
            content = {
                AppScaffold(navController = navBarNavController)
            })

    }
}