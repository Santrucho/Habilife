package com.santrucho.habilife.ui.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santrucho.habilife.ui.data.model.goals.GoalsOption

@Composable
fun CheckOptions(goalsOption: GoalsOption) {
    Row(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (goalsOption.type) {
            "Finance" -> {
                    Text(
                        text = "Monto conseguido:",
                        modifier = Modifier
                            .padding(8.dp)
                            .wrapContentWidth(Alignment.Start),
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                    LinearProgressIndicator(
                        progress = 0.5f, // Ajusta este valor para reflejar el monto conseguido
                        modifier = Modifier
                            .height(16.dp)
                            .width(200.dp)
                    )
                }
            "Work" -> {
                CheckOptionsFields(text = "Puesto a ascender: ", value = "Gerente")
                }
            "Academic" -> {
                CheckOptionsFields(text = "Materias aprobadas: ", value = "4/6")
            }
            "Training" -> {
                CheckOptionsFields(text = "Subir/bajar", value = "25 kilometros")
            }
        }
    }
}

@Composable
fun CheckOptionsFields(text:String,value:String){
    Text(
        text = text,
        modifier = Modifier
            .padding(8.dp)
            .wrapContentWidth(Alignment.Start),
        color = Color.Black,
        fontSize = 12.sp
    )
    Text(
        text = value,
        modifier = Modifier
            .padding(8.dp)
            .wrapContentWidth(Alignment.Start),
        color = Color.Black,
        fontSize = 12.sp
    )
}