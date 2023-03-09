package com.santrucho.habilife.ui.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.presentation.GoalViewModel

@Composable
fun FieldsHelper(type: String, goalViewModel: GoalViewModel) {

    Box(modifier = Modifier.padding(4.dp)) {
        when (type) {
            "Finance" -> {
                FieldsHelperType(
                    text = "Monto objetivo",
                    value = goalViewModel.amountValue
                ) { it.toIntOrNull() }
            }
            "Academic" -> {
                FieldsHelperType(
                    text = "Nombre de la materia",
                    value = goalViewModel.subjectValue
                ) { it }
            }
            "Learning" -> {
                FieldsHelperType(
                    text = "Veces a hacer en la semana",
                    value = goalViewModel.learningValue
                ) { it.toIntOrNull() }
            }
            "Training" -> {
                FieldsHelperType(
                    text = "Kilometros a recorrer: ",
                    value = goalViewModel.trainingValue
                ) { it.toIntOrNull() }
            }
        }
    }
}

@Composable
fun <T> FieldsHelperType(text: String, value: MutableState<T>, valueChange: (String) -> T) {
    OutlinedTextField(
        value = value.value?.toString() ?: "",
        onValueChange = { value.value = valueChange(it) },
        enabled = true,
        label = { Text(text = text) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(1f),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        shape = CircleShape,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            focusedBorderColor = Color.Blue,
            unfocusedBorderColor = Color.Blue,
            focusedLabelColor = Color.Blue,
            unfocusedLabelColor = Color.Blue
        )
    )
}

@Composable
fun ProgressBarHelper(goal: GoalsResponse) {
    when (goal.type) {
        "Finance" -> {
                    CustomLinearProgress(
                        maxProgress = goal.amountGoal?.toFloat(),
                        currentProgress = goal.amount?.toFloat(),
                        colorBar = Color.Yellow,
                        valueType = "%",
                        colorBackground = Color.LightGray,
                        showValues = false
                    )
                }
        "Training" -> {
            CustomLinearProgress(
                maxProgress = goal.kilometers?.toFloat(),
                currentProgress = goal.kilometers?.toFloat(),
                colorBar = Color.Yellow,
                colorBackground = Color.LightGray,
                showValues = false
            )
        }
        /*"Academic" -> {
            CustomLinearProgress(
                maxProgress = goal.subject.toFloat(),
                currentProgress = goal.subject.toFloat(),
                colorBar = Color.Yellow,
                colorBackground = Color.LightGray,
                showValues = false
            )
        }*/
    }
}