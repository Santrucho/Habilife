package com.santrucho.habilife.ui.navigation

//Set each screen as an object to navigate between screens
sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object LoginScreen : Screen("login_screen")
    object SignUpScreen : Screen("signup_screen")
    object NewHabitScreen : Screen ("new_habit_screen")
    object NewGoalScreen : Screen("new_goal_screen")
    object AddGoal:Screen("add_goal_screen")
    object GoalDetail:Screen("goal_detail_screen")
}