package com.santrucho.habilife.ui.navigation

//Set each screen as an object to navigate between screens
sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object SignUpScreen : Screen("signup_screen")
    object NewHabitScreen : Screen ("new_habit_screen")
    object NewGoalScreen:Screen("new_goal_screen")
}