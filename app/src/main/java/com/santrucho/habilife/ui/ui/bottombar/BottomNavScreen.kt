package com.santrucho.habilife.ui.ui.bottombar

import com.santrucho.habilife.R

//Set each screen as an object to display it in the bottom bar
sealed class BottomNavScreen(var title:String, var icon:Int, var screen_route:String){

    object Home : BottomNavScreen("Inicio", R.drawable.ic_home,"home")
    object Habit: BottomNavScreen("Habitos", R.drawable.ic_habits,"habits")
    object Goals: BottomNavScreen("Objetivos", R.drawable.ic_goals,"goals")
    object Profile: BottomNavScreen("Perfil", R.drawable.ic_profile,"profile")

}