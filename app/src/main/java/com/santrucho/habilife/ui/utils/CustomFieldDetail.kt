package com.santrucho.habilife.ui.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.ui.goals.GoalField

@Composable
fun TypeFieldDetail(goal: GoalsResponse, goalViewModel: GoalViewModel) {
    when (goal.type) {
        "Finance" -> {
            GoalField(text = "monto actual", goalText = goal.amount.toString() ?: "")
            CustomField(text = "Agregar monto:",value = goalViewModel.amountValue) {it.toDoubleOrNull()}
        }
        "Training" -> {
            GoalField(text = "Kilometros recorridos: ", goalText = goal.kilometers.toString() ?: "")
            CustomField(text = "Agregar kilometros: ", value = goalViewModel.trainingValue) {it.toIntOrNull()}
        }
        "Academic" -> {
            GoalField(text = "Materias aprobadas: ", goalText = goal.subject ?: "")
            CustomField(text = "Agregar materia: ", value = goalViewModel.subjectValue) {it}
        }
        "Learning" -> {
            GoalField(text = "Veces a hacer en la semana: ", goalText = goal.timesAWeek.toString() ?: "")
            CustomField(text = "Veces que cumplio en esta semana: ", value = goalViewModel.learningValue) {it.toIntOrNull()}
        }
    }
}

@Composable
fun <T> CustomField(text:String,value: MutableState<T?>,valueChange:(String) -> T){
    Card(elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text,
                fontSize = 14.sp,
                color = Color.Black
            )
            TextField(
                value = value.value?.toString() ?: "",
                onValueChange = {
                    value.value = valueChange(it)
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black,
                    focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.Blue,
                    focusedLabelColor = Color.Blue,
                    unfocusedLabelColor = Color.Blue
                )
            )
        }
    }
}