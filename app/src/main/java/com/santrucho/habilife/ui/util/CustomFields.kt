package com.santrucho.habilife.ui.util

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextFields(
    modifier: Modifier = Modifier,
    text: String = "",
    value: String,
    isError: Boolean = false,
    error: String = "",
    shape : Shape = CircleShape,
    isEnabled: Boolean = false,
    placeholder: String = "",
    showErrorText: Boolean = false,
    singleLine: Boolean = false,
    valueChange: (String) -> Unit,
    onValidate: () -> Unit,

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
        placeholder = { Text(text = placeholder) },
        singleLine = singleLine,
        modifier = modifier.fillMaxWidth(),
        enabled = !isEnabled,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        shape = shape,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Gray
        )
    )
    if (!showErrorText)
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = error,
            fontSize = 14.sp,
            color = Color.Red
        )
}

@Composable
fun CustomDescriptionFields(
    modifier: Modifier = Modifier,
    value: String,
    isError: Boolean = false,
    shape : Shape = CircleShape,
    isEnabled: Boolean = false,
    placeholder: String = "",
    singleLine: Boolean = false,
    valueChange: (String) -> Unit,
    onValidate: () -> Unit,

    ) {

//Set the fields to show and fill for create a new habit}
    TextField(
        value = value,
        onValueChange = {
            valueChange(it)
            onValidate()
        },
        isError = isError,
        placeholder = { Text(text = placeholder) },
        singleLine = singleLine,
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f),
        enabled = !isEnabled,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        shape = shape,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Gray,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Gray
        )
    )
}

@Composable
fun <T> CustomPasswordFields(
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

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    leadingIcon: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    value: String,
    onValueChange: (String) -> Unit,
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = {
            val newValue = it.filter { it.isDigit() }
            onValueChange(newValue)
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        textStyle = LocalTextStyle.current.copy(fontSize = 32.sp),
        singleLine = true,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.background)
    )
}

fun formatMoneyInput(input: String): String {
    val digitsOnly = input.replace(".", "").replace(",", "")
    var formatted = ""
    var remaining = digitsOnly
    val numDigits = digitsOnly.length

    // Add the thousands separators
    if (numDigits >= 4) {
        val lastThousandsSeparatorIndex = numDigits % 3
        if (lastThousandsSeparatorIndex != 0) {
            formatted += remaining.substring(0, lastThousandsSeparatorIndex) + "."
            remaining = remaining.substring(lastThousandsSeparatorIndex)
        }
        for (i in remaining.indices step 3) {
            if (i != 0) {
                formatted += "."
            }
            formatted += remaining.substring(i, i + 3)
        }
    } else {
        formatted = remaining
    }

    return formatted
}