package com.santrucho.habilife.ui.utils

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.santrucho.habilife.ui.data.model.Habit

val MustardYellow = Color(0xFFE6B800)
val Green = Color(0xFF005C1B)
val Red = Color(0xFFEB3A0C)
val Pink = Color(0xFFE495F5)
val Dark = Color(0xFF2C332A)
val Blue = Color(0xFF0000FF)
val Orange = Color(0xFFE66E00)
val Purple = Color(0xFF8200F5)

@Composable
fun typeHelper(habitType: String):Color{
    return when(habitType){
        "Salud" -> {
            Red
        }
        "Personal" -> {
            Green
        }
        "Beber" -> {
            Purple
        }
        "Finanzas" -> {
            MustardYellow
        }
        "Social" -> {
            Pink
        }
        "Alimenticio" -> {
            Orange
        }
        "Entrenamiento" -> {
            Blue
        }
        else -> {
            Dark
        }
    }
}