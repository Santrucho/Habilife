package com.santrucho.habilife.ui.data.model

import androidx.compose.ui.graphics.Color

data class Habit(
    var id : String="",
    var userId : String="",
    var title:String="",
    var description:String="",
    var type:HabitType=HabitType.FOOD,
    var frequently:String="",
    var isCompleted : Boolean=false,
    var isExpanded : Boolean=false
) {
}