package com.santrucho.habilife.ui.utils

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
            GoalField(text = "Monto objetivo", goalText = "${goal.amountGoal.toString() ?: ""} $")
            Divider(modifier = Modifier.padding(4.dp))
            GoalField(text = "Monto actual", goalText = "${goal.amount.toString() ?: ""} $")
            Divider(modifier = Modifier.padding(4.dp))
            CustomField(
                text = "Agregar monto:",
                value = goalViewModel.amountValue,
                valueChange = { it.toIntOrNull() },
                isEnabled = goal.amount!! <= goal.amountGoal!!
            )

            Divider(modifier = Modifier.padding(4.dp))

            Text("Progreso", fontSize = 20.sp, color = Color.Blue)
            Spacer(modifier = Modifier.padding(4.dp))
            CustomLinearProgress(
                goal.amountGoal!!.toFloat(),
                goal.amount!!.toFloat(),
                valueType = "$",
                colorBar = Color.Yellow,
                colorBackground = Color.LightGray,
                showValues = true
            )
        }
        "Training" -> {
            GoalField(text = "Kilometros recorridos: ", goalText = goal.kilometers.toString() ?: "")
            CustomField(
                text = "Agregar kilometros: ",
                value = goalViewModel.trainingValue,
                valueChange = { it.toIntOrNull() }
            )
        }
        "Academic" -> {
            GoalField(text = "Materias aprobadas: ", goalText = goal.subject ?: "")
            CustomField(
                text = "Agregar materia: ",
                value = goalViewModel.subjectValue,
                valueChange = { it })
        }
        "Learning" -> {
            GoalField(
                text = "Veces a hacer en la semana: ",
                goalText = goal.timesAWeek.toString() ?: ""
            )
            CustomField(
                text = "Veces que cumplio en esta semana: ",
                value = goalViewModel.learningValue,
                valueChange = { it.toIntOrNull() },
            )
        }
    }
}

@Composable
fun <T> CustomField(
    text: String,
    value: MutableState<T?>,
    valueChange: (String) -> T,
    isEnabled: Boolean = true,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text,
            fontSize = 14.sp,
            color = Color.Blue
        )
        TextField(
            value = value.value?.toString() ?: "",
            onValueChange = {
                value.value = valueChange(it)
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            singleLine = true,
            enabled = isEnabled,
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

@Composable
fun CustomLinearProgress(
    maxProgress: Float?,
    currentProgress: Float?,
    valueType: String = "",
    colorBar : Color,
    colorBackground : Color,
    showValues : Boolean = false
) {
    requireNotNull(maxProgress)
    requireNotNull(currentProgress)

    if(showValues) {
        Row(
            modifier = Modifier
                .widthIn(min = 30.dp)
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${currentProgress.toInt()} $valueType",
                color = Color.Black,
                fontSize = 16.sp
            )
            Text(text = "${maxProgress.toInt()} $valueType", color = Color.Black, fontSize = 16.sp)
        }
    }
    else {
        Row(
            modifier = Modifier
                .widthIn(min = 30.dp)
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(text = "${((currentProgress * 100) / maxProgress).toInt()} $valueType", color = Color.Black, fontSize = 16.sp)
        }
    }
    // Progress Bar
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(17.dp)
    ) {
        // for the background of the ProgressBar
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(9.dp))
                .background(colorBackground)
        )
        // for the progress of the ProgressBar

        Box(
            modifier = Modifier
                .fillMaxWidth(currentProgress / maxProgress)
                .fillMaxHeight()
                .clip(RoundedCornerShape(9.dp))
                .background(colorBar)
                .animateContentSize()
        )
    }
}