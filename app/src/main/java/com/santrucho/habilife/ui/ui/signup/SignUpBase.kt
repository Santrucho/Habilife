package com.santrucho.habilife.ui.ui.signup

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun SignUpBase() {

    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }

    OutlinedTextField(
        value = emailValue.value,
        onValueChange = { emailValue.value = it },
        label = { Text(text = "Username") },
        placeholder = { Text(text = "Nombre de usuario") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(0.8f),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )

    OutlinedTextField(
        value = emailValue.value,
        onValueChange = { emailValue.value = it },
        label = { Text(text = "Email") },
        placeholder = { Text(text = "Email") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(0.8f),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )

    OutlinedTextField(
        value = passwordValue.value,
        onValueChange = { passwordValue.value = it },
        label = { Text(text = "Password") },
        placeholder = { Text(text = "Password") },
        singleLine = true,
        visualTransformation = if (passwordVisibility.value) VisualTransformation.None
        else PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(0.8f)
    )

    OutlinedTextField(
        value = passwordValue.value,
        onValueChange = { passwordValue.value = it },
        label = { Text(text = "Confirm Password") },
        placeholder = { Text(text = "Confirm Password") },
        singleLine = true,
        visualTransformation = if (passwordVisibility.value) VisualTransformation.None
        else PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(0.8f)
    )
}