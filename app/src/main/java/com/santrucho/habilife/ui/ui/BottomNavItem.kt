package com.santrucho.habilife.ui.ui

import com.santrucho.habilife.R

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){

    object Home : BottomNavItem("Home", R.drawable.ic_home,"home")
    object Habit: BottomNavItem("Habits",R.drawable.ic_habits,"habits")
    object Goals: BottomNavItem("Goals",R.drawable.ic_goals,"goals")
    object Profile: BottomNavItem("Profile",R.drawable.ic_profile,"profile")

}