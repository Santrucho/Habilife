package com.santrucho.habilife.ui.ui.habits.components


import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.santrucho.habilife.ui.data.model.Habit
import com.santrucho.habilife.ui.data.model.HabitType

@Composable
fun typeHelper(habit: Habit) : Color {
    when(habit.type){
        HabitType.FOOD -> {
            MaterialTheme.colors.primary
        }
        HabitType.DRINK -> {
            MaterialTheme.colors.onPrimary
        }
        HabitType.SLEEP -> {
            MaterialTheme.colors.onSecondary
        }
    }
    return MaterialTheme.colors.secondary
}