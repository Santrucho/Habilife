package com.santrucho.habilife.ui.ui.habits.components

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.santrucho.habilife.ui.data.model.Habit

@Composable
fun typeHelper(habit: Habit):Color{
    return when(habit.type){
        "Food" -> {
            MaterialTheme.colors.primary
        }
        "Sleep" -> {
            MaterialTheme.colors.onSecondary
        }
        "Drink" -> {
            MaterialTheme.colors.onPrimary
        }
        else -> {
            MaterialTheme.colors.secondary
        }
    }
}