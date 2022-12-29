package com.santrucho.habilife.ui.navigation

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.presentation.HabitViewModel
import com.santrucho.habilife.ui.ui.*
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.ui.login.LoginScreen
import com.santrucho.habilife.ui.presentation.LoginViewModel
import com.santrucho.habilife.ui.ui.signup.SignUpScreen
import com.santrucho.habilife.ui.presentation.SignUpViewModel
import com.santrucho.habilife.ui.ui.habits.HabitScreen
import com.santrucho.habilife.ui.ui.habits.NewHabitScreen
import com.santrucho.habilife.ui.ui.goals.GoalsScreen
import com.santrucho.habilife.ui.ui.goals.NewGoalScreen


@Composable
fun NavigationHost(goalViewModel: GoalViewModel, habitViewModel:HabitViewModel, loginViewModel: LoginViewModel, signUpViewModel: SignUpViewModel, navController:NavController) {

    val isRefreshing = habitViewModel.isRefreshing.collectAsState()

    NavHost(navController = navController as NavHostController, startDestination = Screen.LoginScreen.route,builder ={

        composable(
            route = Screen.LoginScreen.route){
            LoginScreen(
                viewModel = loginViewModel,
                navController = navController
            )
        }
        composable(
            route = Screen.SignUpScreen.route) {
            SignUpScreen(
                viewModel = signUpViewModel,
                navController = navController)
        }

        composable(
            route = Screen.NewHabitScreen.route) {
           NewHabitScreen(
               habitViewModel = habitViewModel,navController = navController
           )
        }
        composable(
            route = Screen.NewGoalScreen.route) {
            NewGoalScreen(goalViewModel = goalViewModel, navController = navController)
        }

        composable(
            route = BottomNavScreen.Home.screen_route,
            content = { HomeScreen(navController) })
        composable(
            route = BottomNavScreen.Habit.screen_route,
            content = { HabitScreen(habitViewModel,navController,isRefreshing.value,refreshData = habitViewModel::getAllHabits) })
        composable(
            route = BottomNavScreen.Goals.screen_route,
            content = { GoalsScreen(goalViewModel,navController,isRefreshing.value, refreshData = goalViewModel::getAllGoals) })
        composable(
            route = BottomNavScreen.Profile.screen_route,
            content = { ProfileScreen(signUpViewModel,loginViewModel,navController) })
    })
}