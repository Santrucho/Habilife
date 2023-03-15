package com.santrucho.habilife.ui.utils

import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
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