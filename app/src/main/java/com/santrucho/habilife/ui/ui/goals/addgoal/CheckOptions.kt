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
        return when (goalsOption.type) {
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
                    Text(
                        text = "Puesto a ascender:",
                        modifier = Modifier
                            .padding(8.dp)
                            .wrapContentWidth(Alignment.Start),
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "Gerente",
                        modifier = Modifier
                            .padding(8.dp)
                            .wrapContentWidth(Alignment.Start),
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                }
            "Academic" -> {
                Text(
                    text = "Materias aprobadas:",
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentWidth(Alignment.Start),
                    color = Color.Black,
                    fontSize = 12.sp
                )
                Text(
                    text = "4/6",
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentWidth(Alignment.Start),
                    color = Color.Black,
                    fontSize = 12.sp
                )
            }
            "Training" -> {
                Text(
                    text = "Subir/bajar:",
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentWidth(Alignment.Start),
                    color = Color.Black,
                    fontSize = 12.sp
                )
                Text(
                    text = "4 kilos",
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentWidth(Alignment.Start),
                    color = Color.Black,
                    fontSize = 12.sp
                )
            }
            else -> {
                Unit
            }
        }
    }
}