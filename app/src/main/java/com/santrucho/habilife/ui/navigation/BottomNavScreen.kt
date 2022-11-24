package com.santrucho.habilife.ui.navigation

import com.santrucho.habilife.R

sealed class BottomNavScreen(var title:String, var icon:Int, var screen_route:String){

    object Home : BottomNavScreen("Home", R.drawable.ic_home,"home")
    object Habit: BottomNavScreen("Habits", R.drawable.ic_habits,"habits")
    object Goals: BottomNavScreen("Goals", R.drawable.ic_goals,"goals")
    object Profile: BottomNavScreen("Profile", R.drawable.ic_profile,"profile")

}