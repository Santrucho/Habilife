package com.santrucho.habilife.ui.ui.habits.components

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.santrucho.habilife.ui.data.model.Habit

@Composable
fun typeHelper(habitType: String):Color{
    return when(habitType){
        "Salud" -> {
            MaterialTheme.colors.primary
        }
        "Finanzas" -> {
            MaterialTheme.colors.onSecondary
        }
        "Personal" -> {
            MaterialTheme.colors.onPrimary
        }
        "Relaciones" -> {
            MaterialTheme.colors.onBackground
        }
        "Sueño" -> {
            MaterialTheme.colors.onSecondary
        }
        "Otros" -> {
            MaterialTheme.colors.onBackground
        }
        else -> {
            MaterialTheme.colors.secondary
        }
    }
}