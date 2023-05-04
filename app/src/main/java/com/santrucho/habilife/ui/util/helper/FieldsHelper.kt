package com.santrucho.habilife.ui.util

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.santrucho.habilife.ui.presentation.GoalViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun FieldsHelper(type: String, goalViewModel: GoalViewModel) {

    val context = LocalContext.current
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
                        val maxLength = 8
                        Text("Monto objetivo:")
                        InputField(
                            modifier = Modifier.fillMaxWidth().padding(4.dp),
                            leadingIcon = {Text(text="$",fontSize = 24.sp)},
                            trailingIcon = { Text(text =",00", fontSize = 24.sp) },
                            value = goalViewModel.amountValue.value?.toString()?.let { formatMoneyInput(it) } ?: "",
                            onValueChange = { newValue ->
                                val intValue = newValue.toIntOrNull()
                                if (intValue != null && intValue.toString().length > maxLength) {
                                    Toast.makeText(context, "El valor máximo es de $maxLength digitos", Toast.LENGTH_SHORT).show()
                                    return@InputField
                                }
                                goalViewModel.amountValue.value = intValue
                            }
                        )
                    }
                    "Academic" -> {
                        Row(
                            Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            CustomTextFields(text = "Agregar materia",
                                value = goalViewModel.subjectValue.value ?: "",
                                modifier = Modifier
                                    .fillMaxWidth(0.8f),
                                showErrorText = false,
                                singleLine = true,
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
                            LazyColumn(modifier = Modifier.wrapContentHeight()) {
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
                        Text("Veces a hacer en la semana:")
                        InputField(
                            modifier = Modifier.fillMaxWidth().padding(4.dp),
                            value = goalViewModel.learningValue.value?.toString() ?: "",
                            onValueChange = { newValue ->
                                val intValue = newValue.toIntOrNull()
                                goalViewModel.learningValue.value = intValue })
                    }
                    "Training" -> {
                        Text("Kilometros a recorrer:")
                        InputField(
                            modifier = Modifier.fillMaxWidth().padding(4.dp),
                            trailingIcon = { Text(text ="Km", fontSize = 24.sp) },
                            value = goalViewModel.trainingValue.value?.toString() ?: "",
                            onValueChange = { newValue ->
                                val intValue = newValue.toIntOrNull()
                                goalViewModel.trainingValue.value = intValue })
                    }
                }
            }
        }
    }
}


