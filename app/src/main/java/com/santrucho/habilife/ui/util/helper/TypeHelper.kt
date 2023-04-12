package com.santrucho.habilife.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

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

@Composable
fun placeholderType(type:Color):String{
    return when(type){
        Red -> {
            "Dormir 8 horas"
        }
        Green -> {
            "Llamar a la abuela"
        }
        Purple -> {
            "Beber 2 litros de agua"
        }
        MustardYellow -> {
            "Evitar gastos basura"
        }
        Pink -> {
            "Llamar a la abuela"
        }
        Orange -> {
            "Comer frutas"
        }
        Blue -> {
            "Ir al gimnasio"
        }
        else -> {
            "Otros"
        }
    }
}