package com.santrucho.habilife.ui.navigation

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object SignUpScreen : Screen("signup_screen")
    object NewHabitScreen : Screen ("new_habit_screen")
}