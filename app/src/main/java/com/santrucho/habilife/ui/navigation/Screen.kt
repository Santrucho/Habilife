package com.santrucho.habilife.ui.navigation

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object AppScaffold : Screen("app_scaffold")

}