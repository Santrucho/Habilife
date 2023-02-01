package com.santrucho.habilife.ui.navigation

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.santrucho.habilife.ui.data.model.Habit
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@Composable
fun NavigationHost(navController:NavController) {


    val goalViewModel = viewModel<GoalViewModel>()
    val signUpViewModel = viewModel<SignUpViewModel>()
    val habitViewModel = viewModel<HabitViewModel>()
    val loginViewModel = viewModel<LoginViewModel>()
    val isRefreshing = habitViewModel.isRefreshing.collectAsState()
    val habitsStateFlow = MutableStateFlow<List<Habit>>(emptyList())

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
                navController = navController)
        }
        composable(
            route = Screen.NewHabitScreen.route,
            content = { NewHabitScreen(habitViewModel = habitViewModel,navController = navController)})
        composable(
            route = Screen.NewGoalScreen.route,
            content = { NewGoalScreen(goalViewModel = goalViewModel, navController = navController) })

        composable(
            route = BottomNavScreen.Home.screen_route,
            content = { HomeScreen(navController) })
        composable(
            route = BottomNavScreen.Habit.screen_route,
            content = { HabitScreen(habitViewModel,navController,isRefreshing.value,refreshData = habitViewModel::getAllHabits,habitsStateFlow) })
        composable(
            route = BottomNavScreen.Goals.screen_route,
            content = { GoalsScreen(goalViewModel,navController,isRefreshing.value, refreshData = goalViewModel::getAllGoals) })
        composable(
            route = BottomNavScreen.Profile.screen_route,
            content = {
                ProfileScreen(navController,loginViewModel, signUpViewModel)
            })
    })
}