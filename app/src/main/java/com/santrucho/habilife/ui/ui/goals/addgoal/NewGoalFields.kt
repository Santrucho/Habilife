package com.santrucho.habilife.ui.ui.goals.components

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
import androidx.compose.ui.unit.sp

@Composable
fun <T> NewFields(
    text: String,
    value: MutableState<T>,
    isError: MutableState<Boolean>,
    error: MutableState<T>,
    valueChange: (String) -> T, onValidate: () -> Unit
) {

//Set the fields to show and fill for create a new habit}
    OutlinedTextField(
        value = value.value.toString() ?: "",
        onValueChange = {
            value.value = valueChange(it)
            onValidate()
        },
        label = { Text(text = text) },
        isError = isError.value,
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
    Text(
        modifier = Modifier.padding(start = 8.dp),
        text = error.value.toString(),
        fontSize = 14.sp,
        color = Color.Red
    )
}