package com.santrucho.habilife.ui.navigation

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object SignUpScreen : Screen("signup_screen")
    object NewGoalScreen:Screen("new_goal_screen")
}