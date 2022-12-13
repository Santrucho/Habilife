package com.santrucho.habilife.ui.ui.signup

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santrucho.habilife.R
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.utils.Resource

@Composable
fun SignUpScreen(viewModel:SignUpViewModel, navController: NavController) {

    var usernameValue by remember { mutableStateOf("") }
    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    var confirmPasswordValue by remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }

    val signUpFlow = viewModel.signUpFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            OutlinedTextField(
                value = usernameValue,
                onValueChange = { usernameValue = it },
                label = { Text(text = "Username") },
                placeholder = { Text(text = "Nombre de usuario") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.8f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            OutlinedTextField(
                value = emailValue,
                onValueChange = { emailValue = it },
                label = { Text(text = "Email") },
                placeholder = { Text(text = "Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.8f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            OutlinedTextField(
                value = passwordValue,
                onValueChange = { passwordValue = it },
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Password") },
                singleLine = true,
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            OutlinedTextField(
                value = confirmPasswordValue,
                onValueChange = { confirmPasswordValue = it },
                label = { Text(text = "Confirm Password") },
                placeholder = { Text(text = "Confirm Password") },
                singleLine = true,
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.padding(10.dp))

            Button(
                onClick = {
                    viewModel.signUp(usernameValue,emailValue,passwordValue)
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
            ) {
                Text(
                    text = "Create account"
                )
            }
        }
        Spacer(modifier = Modifier.padding(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .wrapContentHeight(), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "You already have account? Sign in",
                modifier = Modifier.clickable(onClick = {
                    navController.navigate(Screen.LoginScreen.route)
                }), color = Color.Black, fontSize = 14.sp
            )
        }
    }
    signUpFlow.value?.let{
        when(it){
            is Resource.Success ->{
                LaunchedEffect(Unit){
                    navController.navigate(BottomNavScreen.Home.screen_route){
                        popUpTo(Screen.SignUpScreen.route) {inclusive = true}
                    }
                }
            }
            is Resource.Failure -> {
                val context = LocalContext.current
                Toast.makeText(context,it.exception.message,Toast.LENGTH_LONG).show()
            }
            is Resource.Loading -> {
                Box(contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }

            }
        }
    }
}