package com.santrucho.habilife.ui

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.work.HiltWorker
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging
import com.santrucho.habilife.ui.navigation.NavigationHost
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.presentation.LoginViewModel
import com.santrucho.habilife.ui.presentation.SignUpViewModel
import com.santrucho.habilife.ui.theme.HabilifeTheme
import com.santrucho.habilife.ui.ui.bottombar.BottomBar
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.util.LogBundle
import com.santrucho.habilife.ui.util.manager.HabitScheduler
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val firebaseAnalytics : FirebaseAnalytics = FirebaseAnalytics.getInstance(this)
    private val schedule = HabitScheduler(this)

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        schedule.startUpdateFieldWorker()
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
                        when(currentState) {
                            BottomNavScreen.Habit.screen_route -> {
                                FloatingActionButton(backgroundColor = MaterialTheme.colors.primary, onClick = { navController.navigate(Screen.NewHabitScreen.route)
                                    LogBundle.logBundleAnalytics(firebaseAnalytics,"New Habit","new_habit_pressed")
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Crear nuevo habito",
                                        tint = MaterialTheme.colors.background
                                    )
                                }
                            }
                            BottomNavScreen.Goals.screen_route -> {
                                FloatingActionButton(backgroundColor = MaterialTheme.colors.primary,onClick = { navController.navigate(Screen.NewGoalScreen.route)
                                    LogBundle.logBundleAnalytics(firebaseAnalytics,"New Goal","new_goal_pressed")
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Crear nuevo goal",
                                        tint = MaterialTheme.colors.background
                                    )
                                }
                            }
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
