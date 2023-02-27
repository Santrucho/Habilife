package com.santrucho.habilife.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.santrucho.habilife.ui.presentation.*
import com.santrucho.habilife.ui.ui.*
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.ui.goals.addgoal.GoalDetail
import com.santrucho.habilife.ui.ui.login.LoginScreen
import com.santrucho.habilife.ui.ui.signup.SignUpScreen
import com.santrucho.habilife.ui.ui.habits.HabitScreen
import com.santrucho.habilife.ui.ui.habits.NewHabitScreen
import com.santrucho.habilife.ui.ui.goals.GoalsScreen
import com.santrucho.habilife.ui.ui.goals.addgoal.NewGoalScreen
import com.santrucho.habilife.ui.ui.home.HomeScreen


@Composable
fun NavigationHost(navController:NavController) {

    val goalViewModel = viewModel<GoalViewModel>()
    val signUpViewModel = viewModel<SignUpViewModel>()
    val habitViewModel = viewModel<HabitViewModel>()
    val loginViewModel = viewModel<LoginViewModel>()

    NavHost(navController = navController as NavHostController, startDestination = Screen.LoginScreen.route,builder ={

        composable(
            route = Screen.LoginScreen.route){
            LoginScreen(
                viewModel = loginViewModel,
                navController = navController,
            )
        }
        composable(
            route = Screen.SignUpScreen.route) {
            SignUpScreen(
                navController = navController,signUpViewModel)
        }
        composable(
            route = Screen.NewHabitScreen.route,
            content = { NewHabitScreen(habitViewModel = habitViewModel,navController = navController)})
        composable(
            route = Screen.NewGoalScreen.route,
            content = { NewGoalScreen(goalViewModel = goalViewModel,navController) })

        composable(
            route = "goal_detail_screen/{type}",
            arguments = listOf(navArgument("type"){type = NavType.StringType}))
            { backStackEntry ->
                val type = backStackEntry.arguments?.getString("type")
                requireNotNull(type)
                GoalDetail(goalViewModel = goalViewModel,navController,type)
            }

        composable(
            route = BottomNavScreen.Home.screen_route,
            content = { HomeScreen(navController,signUpViewModel,goalViewModel,habitViewModel) })
        composable(
            route = BottomNavScreen.Habit.screen_route,
            content = { HabitScreen(habitViewModel,navController) })
        composable(
            route = BottomNavScreen.Goals.screen_route,
            content = { GoalsScreen(goalViewModel,navController) })
        composable(
            route = BottomNavScreen.Profile.screen_route,
            content = {
                ProfileScreen(navController,goalViewModel,habitViewModel,loginViewModel, signUpViewModel)
            })
    })
}