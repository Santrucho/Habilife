package com.santrucho.habilife.ui.util

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santrucho.habilife.ui.data.model.goals.GoalsResponse
import com.santrucho.habilife.ui.presentation.GoalViewModel
import com.santrucho.habilife.ui.ui.goals.GoalField
import com.santrucho.habilife.ui.ui.goals.GoalsComplete
import com.santrucho.habilife.ui.ui.goals.components.NewFields
import com.santrucho.habilife.ui.util.helper.CustomLinearProgress

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun TypeFieldDetail(goal: GoalsResponse, goalViewModel: GoalViewModel) {

    // Recordar si se ha mostrado el diálogo de completado
    val isCompletedDialogOpen = remember { mutableStateOf(false) }

    when (goal.type) {
        "Finance" -> {

            var moneyCount by remember { mutableStateOf(goal.amount?.toFloat() ?: 0f) }

            // Actualizar el valor de kilometersCount cada vez que el valor de trainingValue cambia
            LaunchedEffect(goalViewModel.amountValue.value) {
                if (!goal.completed) { // Agregar esta comprobación adicional
                    moneyCount = (goalViewModel.amountValue.value ?: 0) + (goal.amount ?: 0).toFloat()
                    if (moneyCount >= (goal.amountGoal?.toFloat() ?: 0f)) {
                        goalViewModel.completeGoal(goal,true)
                        isCompletedDialogOpen.value = true
                    }
                }
            }

            // Verificar si el objetivo ha sido completado y el diálogo aún no se ha mostrado
            if (isCompletedDialogOpen.value) {
                GoalsComplete(goal.title, goalViewModel) {
                    // Aquí puedes realizar cualquier acción necesaria después de cerrar el diálogo
                    isCompletedDialogOpen.value = false
                }
            }
            GoalField(text = "Monto objetivo", goalText = "${goal.amountGoal.toString()} $")

            Divider(modifier = Modifier.padding(4.dp))
            GoalField(text = "Monto actual", goalText = "${goal.amount ?: 0} $")

            Divider(modifier = Modifier.padding(4.dp))

            Column(modifier = Modifier.padding(4.dp)) {
                Text("Progreso", fontSize = 20.sp, color = MaterialTheme.colors.secondary)

                CustomLinearProgress(
                    goal.amountGoal?.toFloat(),
                    currentProgress = if(moneyCount <= (goal.amountGoal?.toFloat() ?: Float.MAX_VALUE)) moneyCount else goal.amountGoal?.toFloat(),
                    valueType = "$",
                    showValues = true
                )
            }

            Divider(modifier = Modifier.padding(4.dp))
            Spacer(modifier = Modifier.padding(4.dp))
            NewFields(
                text = "Agregar monto:",
                value = goalViewModel.amountValue.value?.toString() ?: "",
                valueChange = { goalViewModel.amountValue.value = it.toIntOrNull() },
                onValidate = {},
                isEnabled = (goal.amount ?: 0) >= (goal.amountGoal ?: 0)
            )
        }
        "Training" -> {
            var kilometersCount by remember { mutableStateOf(goal.kilometers?.toFloat() ?: 0f) }

            // Actualizar el valor de kilometersCount cada vez que el valor de trainingValue cambia
            LaunchedEffect(goalViewModel.trainingValue.value) {
                if(!goal.completed) {
                    kilometersCount =
                        (goalViewModel.trainingValue.value ?: 0) + (goal.kilometers ?: 0).toFloat()
                    if (kilometersCount >= (goal.kilometersGoal?.toFloat() ?: 0f)) {
                        goalViewModel.completeGoal(goal,true)
                        isCompletedDialogOpen.value = true
                        // Limitar el valor máximo de kilometersCount a goal.kilometersGoal
                        kilometersCount = goal.kilometersGoal?.toFloat() ?: 0f
                    }
                }
            }
            // Verificar si el objetivo ha sido completado y el diálogo aún no se ha mostrado
            if (isCompletedDialogOpen.value) {
                GoalsComplete(goal.title, goalViewModel) {
                    // Aquí puedes realizar cualquier acción necesaria después de cerrar el diálogo
                    isCompletedDialogOpen.value = false
                }
            }

            GoalField(
                text = "Kilometros a correr",
                goalText = goal.kilometersGoal.toString() ?: ""
            )
            Divider(modifier = Modifier.padding(4.dp))
            GoalField(
                text = "Kilometros recorridos",
                goalText = "${goal.kilometers ?: 0} Km"
            )
            Divider(modifier = Modifier.padding(4.dp))
            Column(modifier = Modifier.padding(4.dp)) {
                Text("Progreso", fontSize = 20.sp, color = MaterialTheme.colors.secondary)
                Spacer(modifier = Modifier.padding(4.dp))

                CustomLinearProgress(
                    goal.kilometersGoal?.toFloat(),
                    kilometersCount,
                    valueType = "Km",
                    showValues = true
                )
            }
            Divider(modifier = Modifier.padding(4.dp))
            Spacer(modifier = Modifier.padding(4.dp))
            NewFields(
                text = "Agregar kilometros: ",
                value = goalViewModel.trainingValue.value?.toString() ?: "",
                valueChange = {
                    goalViewModel.trainingValue.value = it.toIntOrNull()
                    kilometersCount = goalViewModel.trainingValue.value?.toFloat() ?: 0f
                },
                onValidate = {},
                isEnabled = (goal.kilometers ?: 0) >= (goal.kilometersGoal ?: 0)
            )

        }
        "Academic" -> {
            var subjectApprove = goalViewModel.subjectApproved.collectAsState()

            val listSum = if (goalViewModel.confirmSubject.value) {
                subjectApprove.value.union(goal.subjectApproved).toMutableList()
            } else {
                goal.subjectApproved.toMutableList()
            }
            var subjectApprovedCount by remember { mutableStateOf(goal.subjectApproved.size.toFloat()) }

            Column(modifier = Modifier.padding(4.dp)) {
                Text("Progreso", fontSize = 20.sp, color = MaterialTheme.colors.secondary)
                CustomLinearProgress(
                    maxProgress = goal.subjectList?.size?.toFloat(),
                    currentProgress = subjectApprovedCount,
                    valueType = "%"
                )
            }

            Spacer(modifier = Modifier.padding(4.dp))
            Divider(modifier = Modifier.padding(4.dp))

            GoalField(
                text = "Materias a aprobar: ",
                goalText = goal.subjectList?.size.toString() ?: ""
            )
            Divider(modifier = Modifier.padding(4.dp))

            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = "Materias",
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                itemsIndexed(goal.subjectList.orEmpty()) { _, subject ->

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                            text = "- $subject",
                            color = if (listSum.contains(subject)) Color.Gray else Color.Black,
                            fontSize = 16.sp,
                            textDecoration = if (listSum.contains(subject)) TextDecoration.LineThrough else TextDecoration.None,
                        )

                        Checkbox(checked = listSum.contains(subject),
                            onCheckedChange = {

                                if (it) {
                                    goalViewModel.subjectApproved(subject)
                                    listSum.add(subject)
                                    subjectApprovedCount++
                                } else {
                                    goalViewModel.updateSubjectApproved(subject)
                                    listSum.remove(subject)
                                    subjectApprovedCount--
                                }

                            })
                    }
                    Divider(modifier = Modifier.padding(4.dp))
                }
                goal.subjectApproved = listSum
            }

            // Actualizar el valor de subjectApprovedCount cada vez que el valor de subjectApproved cambia
            LaunchedEffect(goalViewModel.subjectApproved.value) {
                if(!goal.completed) {
                    subjectApprovedCount = goal.subjectApproved.size.toFloat()
                    if (subjectApprovedCount >= (goal.subjectList?.size?.toFloat() ?: 0f)) {
                        goalViewModel.completeGoal(goal,true)
                        isCompletedDialogOpen.value = true
                    }
                }
            }
            // Verificar si el objetivo ha sido completado y el diálogo aún no se ha mostrado
            if (isCompletedDialogOpen.value) {
                GoalsComplete(goal.title, goalViewModel) {
                    // Aquí puedes realizar cualquier acción necesaria después de cerrar el diálogo
                    isCompletedDialogOpen.value = false
                }
            }
        }
        "Learning" -> {
            GoalField(
                text = "Veces a hacer en la semana: ",
                goalText = goal.timesAWeek.toString() ?: ""
            )
            NewFields(
                text = "Veces que cumplio en esta semana: ",
                value = goalViewModel.learningValue.value?.toString() ?: "",
                valueChange = { goalViewModel.learningValue.value = it.toIntOrNull() },
                onValidate = {}
            )
        }
    }
}

