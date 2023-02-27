package com.santrucho.habilife.ui.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun FieldsHelper(type: String, subject:MutableState<String> = mutableStateOf(""),
                 amountValue:MutableState<Int?> = mutableStateOf(null)) {

    Box(modifier = Modifier.padding(4.dp)) {
        return when (type) {
            "Finance" -> {
                OutlinedTextField(
                    value = amountValue.value.toString(),
                    onValueChange = { amountValue.value = it.toInt() },
                    enabled = true,
                    label = { Text(text = "Monto") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
            "Academic" -> {
                OutlinedTextField(
                    value = subject.value,
                    onValueChange = { subject.value = it },
                    enabled = true,
                    label = { Text(text = "Nombre de la materia") },
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
            else -> {
                Unit
            }
        }
    }
}