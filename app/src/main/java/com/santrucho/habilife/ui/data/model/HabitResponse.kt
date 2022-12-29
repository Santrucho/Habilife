package com.santrucho.habilife.ui.data.model

data class HabitResponse(
    val isLoading : Boolean = false,
    val listHabits : List<Habit> = emptyList(),
    val error : String = ""
) {
}