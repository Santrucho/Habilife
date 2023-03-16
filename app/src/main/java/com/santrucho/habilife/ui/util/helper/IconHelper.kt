package com.santrucho.habilife.ui.util

import androidx.compose.runtime.Composable
import com.santrucho.habilife.R

@Composable
fun iconHelper(habitType: String): Int {
    return when(habitType){
        "Salud" -> {
            R.drawable.ic_heart_outline
        }
        "Personal" -> {
            R.drawable.ic_human_handsup
        }
        "Beber" -> {
            R.drawable.ic_water
        }
        "Finanzas" -> {
           R.drawable.ic_cash
        }
        "Social" -> {
            R.drawable.ic_human_male_female
        }
        "Alimenticio" -> {
            R.drawable.ic_egg_fried
        }
        "Entrenamiento" -> {
           R.drawable.ic_gym
        }
        else -> {
            R.drawable.ic_pencil_outline
        }
    }
}