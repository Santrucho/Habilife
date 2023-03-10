package com.santrucho.habilife.ui.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.ui.goals.components.NewFields

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FieldsHelper(type: String, goalViewModel: GoalViewModel) {

    Box(modifier = Modifier.padding(4.dp)) {
        when (type) {
            "Finance" -> {
                NewFields(
                    text = "Monto objetivo",
                    value = goalViewModel.amountValue.value?.toString() ?: "",
                    valueChange = { goalViewModel.amountValue.value = it.toIntOrNull() },
                    onValidate = {},
                    showErrorText = false
                )
            }
            "Academic" -> {
                Card(modifier = Modifier
                    .fillMaxWidth(), elevation = 8.dp) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            NewFields(text = "Agregar materia",
                                value = goalViewModel.subjectValue.value ?: "",
                                modifier = Modifier
                                    .fillMaxWidth(0.8f),
                                showErrorText = false,
                                valueChange = { goalViewModel.subjectValue.value = it },
                                onValidate = {})

                            IconButton(
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .height(56.dp),
                                onClick = {
                                    goalViewModel.addSubject(goalViewModel.subjectValue)
                                    goalViewModel.subjectValue.value = ""
                                },
                                enabled = !goalViewModel.subjectValue.value.isNullOrEmpty()
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .height(32.dp)
                                        .width(32.dp),
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add button",
                                    tint = Color.Blue
                                )
                            }
                        }
                        if (goalViewModel.subjectList.value.isNotEmpty()) {

                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = "Materias:",
                                fontWeight = FontWeight.Medium,
                                color = Color.Black,
                                fontSize = 20.sp
                            )
                            LazyColumn(modifier = Modifier.fillMaxHeight(0.7f)){
                                itemsIndexed(goalViewModel.subjectList.value) { _, subject ->
                                    Divider(modifier = Modifier.padding(4.dp))
                                    Text(
                                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
                                        text = "- $subject",
                                        color = Color.Black,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
            "Learning" -> {
                NewFields(
                    text = "Veces a hacer en la semana",
                    value = goalViewModel.learningValue.value?.toString() ?: "",
                    valueChange = { goalViewModel.learningValue.value = it.toIntOrNull() },
                    onValidate = {},
                    showErrorText = false
                )
            }
            "Training" -> {
                NewFields(
                    text = "Kilometros a recorrer: ",
                    value = goalViewModel.trainingValue.value?.toString() ?: "",
                    valueChange = { goalViewModel.trainingValue.value = it.toIntOrNull() },
                    onValidate = {},
                    showErrorText = false)
            }
        }
    }
}

@Composable
fun ProgressBarHelper(goal: GoalsResponse) {
    when (goal.type) {
        "Finance" -> {
            CustomLinearProgress(
                maxProgress = goal.amountGoal?.toFloat(),
                currentProgress = goal.amount?.toFloat(),
                colorBar = Color.Green,
                valueType = "%",
                colorBackground = Color.LightGray,
                showValues = false
            )
        }
        "Training" -> {
            CustomLinearProgress(
                maxProgress = goal.kilometersGoal?.toFloat(),
                currentProgress = goal.kilometers?.toFloat(),
                colorBar = Color.Blue,
                valueType = "%",
                colorBackground = Color.LightGray,
                showValues = false
            )
        }
        "Academic" -> {
            CustomLinearProgress(
                maxProgress = goal.subjectList?.size?.toFloat(),
                currentProgress = goal.subjectApproved.size.toFloat(),
                colorBar = Color.Cyan,
                colorBackground = Color.LightGray,
                showValues = false
            )
        }
    }
}