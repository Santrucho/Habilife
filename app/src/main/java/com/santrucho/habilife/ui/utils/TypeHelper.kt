package com.santrucho.habilife.ui.utils

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.santrucho.habilife.ui.data.model.Habit

val LightSteelYellow = Color(0xFFE0E0A0)
val MustardYellow = Color(0xFFE6B800)
val LightGreen = Color(0xFF3CEB54)
val Red = Color(0xFFEB3A0C)
val Pink = Color(0xFFE495F5)
val Dark = Color(0xFF2C332A)
val Blue = Color(0xFF0000FF)

@Composable
fun typeHelper(habitType: String):Color{
    return when(habitType){
        "Relaciones" -> {
            Color(0xFFE0E0A0)
        }
        "Finanzas" -> {
            Color(0xFFE6B800)
        }
        "Personal" -> {
            Color(0xFF3CEB54)
        }
        "Salud" -> {
            Color(0xFFEB3A0C)
        }
        "Sueño" -> {
            Color(0xFFE495F5)
        }
        "Otros" -> {
            Color(0xFF2C332A)
        }
        else -> {
            Dark
        }
    }
}