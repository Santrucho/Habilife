package com.santrucho.habilife.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.santrucho.habilife.ui.navigation.NavigationHost
import com.santrucho.habilife.ui.theme.HabilifeTheme
import com.santrucho.habilife.ui.ui.bottombar.BottomBar
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.presentation.LoginViewModel
import com.santrucho.habilife.ui.presentation.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val signUpViewModel by viewModels<SignUpViewModel>()
    private val loginViewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HabilifeTheme {
                val navItems = listOf(
                    BottomNavScreen.Home,
                    BottomNavScreen.Habit,
                    BottomNavScreen.Goals,
                    BottomNavScreen.Profile
                )
                val navController = rememberNavController()
                Scaffold(bottomBar = {
                    val items = navItems.map{
                        it.screen_route
                    }
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentState = navBackStackEntry?.destination?.hierarchy?.first()?.route

                    if(currentState !in items) return@Scaffold

                    BottomBar(
                        items = navItems,
                        navController = navController,
                        onClick = {
                            navController.navigate(it.screen_route)
                        }
                    )
                }){
                    NavigationHost(loginViewModel,signUpViewModel,navController)
                }
            }
        }
    }
}
