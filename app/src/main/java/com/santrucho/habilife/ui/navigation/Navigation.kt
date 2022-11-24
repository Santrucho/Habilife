package com.santrucho.habilife.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.santrucho.habilife.ui.ui.*


@Composable
fun Navigation(navController:NavController) {

    NavHost(navController = navController as NavHostController, startDestination = BottomNavScreen.Home.screen_route,builder ={
        composable(
            route = BottomNavScreen.Home.screen_route,
            content = { HomeScreen(navController) })
        composable(
            route = BottomNavScreen.Habit.screen_route,
            content = { HabitScreen(navController) })
        composable(
            route = BottomNavScreen.Goals.screen_route,
            content = { GoalsScreen(navController) })
        composable(
            route = BottomNavScreen.Profile.screen_route,
            content = { ProfileScreen(navController) })
    })
}