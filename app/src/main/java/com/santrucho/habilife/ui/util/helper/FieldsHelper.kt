package com.santrucho.habilife.ui.util

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.ui.goals.components.TextFields

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FieldsHelper(type: String, goalViewModel: GoalViewModel) {

    Box(modifier = Modifier.padding(4.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth(), elevation = 8.dp
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp,vertical = 2.dp),
                verticalArrangement = Arrangement.Center
            ) {
                when (type) {
                    "Finance" -> {
                        TextFields(
                            text = "Monto objetivo",
                            value = goalViewModel.amountValue.value?.toString() ?: "",
                            valueChange = { goalViewModel.amountValue.value = it.toIntOrNull() },
                            onValidate = {},
                            showErrorText = false
                        )
                    }
                    "Academic" -> {
                        Row(
                            Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextFields(text = "Agregar materia",
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
                                    tint = MaterialTheme.colors.primary
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

                            val subjectList by goalViewModel.subjectList.collectAsState()
                            LazyColumn(modifier = Modifier.fillMaxHeight(0.7f)) {
                                itemsIndexed(subjectList) { _, subject ->
                                    Divider(modifier = Modifier.padding(4.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            modifier = Modifier.padding(
                                                vertical = 4.dp,
                                                horizontal = 16.dp
                                            ),
                                            text = "- $subject",
                                            color = Color.Black,
                                            fontSize = 16.sp
                                        )
                                        IconButton(onClick = {
                                            goalViewModel.deleteSubject(subject)
                                            subjectList.toMutableList().remove(subject)
                                        }) {
                                            Icon(
                                                imageVector = Icons.Outlined.Delete,
                                                modifier = Modifier.width(28.dp),
                                                contentDescription = "delete subject",
                                                tint = MaterialTheme.colors.primary,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    "Learning" -> {

                        TextFields(
                            text = "Veces a hacer en la semana",
                            value = goalViewModel.learningValue.value?.toString() ?: "",
                            valueChange = { goalViewModel.learningValue.value = it.toIntOrNull() },
                            onValidate = {},
                            showErrorText = false
                        )

                    }
                    "Training" -> {

                        TextFields(
                            text = "Kilometros a recorrer: ",
                            value = goalViewModel.trainingValue.value?.toString() ?: "",
                            valueChange = { goalViewModel.trainingValue.value = it.toIntOrNull() },
                            onValidate = {},
                            showErrorText = false
                        )

                    }
                }
            }
        }
    }
}


