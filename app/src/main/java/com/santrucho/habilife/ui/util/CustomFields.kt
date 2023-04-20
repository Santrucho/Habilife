package com.santrucho.habilife.ui.ui.goals.components

import android.util.Log
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextFields(
    modifier : Modifier = Modifier,
    text: String = "",
    value: String,
    isError: Boolean = false,
    error: String = "",
    isEnabled: Boolean = false,
    placeholder: String = "",
    showErrorText: Boolean = false,
    valueChange: (String) -> Unit,
    onValidate: () -> Unit
) {

//Set the fields to show and fill for create a new habit}
    OutlinedTextField(
        value = value,
        onValueChange = {
            valueChange(it)
            onValidate()
        },
        label = { Text(text = text) },
        isError = isError,
        placeholder = {Text( text = placeholder)},
        singleLine = true,
        modifier = modifier.fillMaxWidth(1f),
        enabled = !isEnabled,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        shape = CircleShape,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Gray
        )
    )
    if(!showErrorText)
    Text(
        modifier = Modifier.padding(start = 8.dp),
        text = error,
        fontSize = 14.sp,
        color = Color.Red
    )
}

@Composable
fun <T> PasswordFields(
    text: String,
    value: MutableState<T>,
    isError: MutableState<Boolean>,
    error: MutableState<T>,
    passwordVisibility: MutableState<Boolean>,
    valueChange: (String) -> T, onValidate: () -> Unit
) {

//Set the fields to show and fill for create a new habit}
    OutlinedTextField(
        value = value.value.toString(),
        onValueChange = {
            value.value = valueChange(it)
            onValidate()
        },
        label = { Text(text = text) },
        isError = isError.value,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(1f),
        visualTransformation = if (passwordVisibility.value) VisualTransformation.None
        else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        shape = CircleShape,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            focusedBorderColor = Color.Black,
            errorCursorColor = Color.Red,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Gray
        )
    )
    Text(
        modifier = Modifier.padding(start = 8.dp),
        text = error.value.toString(),
        fontSize = 14.sp,
        color = Color.Red
    )
}