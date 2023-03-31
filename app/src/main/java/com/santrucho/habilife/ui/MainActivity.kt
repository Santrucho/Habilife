package com.santrucho.habilife.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.santrucho.habilife.ui.navigation.NavigationHost
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.presentation.LoginViewModel
import com.santrucho.habilife.ui.presentation.SignUpViewModel
import com.santrucho.habilife.ui.theme.HabilifeTheme
import com.santrucho.habilife.ui.ui.bottombar.BottomBar
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentState = navBackStackEntry?.destination?.hierarchy?.first()?.route


                Scaffold(bottomBar = {
                    val items = navItems.map {
                        it.screen_route
                    }
                    if (currentState !in items) return@Scaffold
                    BottomBar(
                        items = navItems,
                        navController = navController,
                        onClick = {
                            navController.navigate(it.screen_route)
                        }
                    )
                },
                    floatingActionButton = {
                        FloatingActionButton(onClick = { /* Tus acciones */ },backgroundColor = MaterialTheme.colors.primary) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Crear nota"
                            )
                        }
                    },
                    floatingActionButtonPosition = FabPosition.End
                ){
                    NavigationHost(navController)
                }
            }
        }
    }
}
