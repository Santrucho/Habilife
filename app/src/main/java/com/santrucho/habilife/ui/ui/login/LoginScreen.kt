package com.santrucho.habilife.ui.ui.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import com.santrucho.habilife.ui.presentation.LoginViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.utils.Resource


@Composable
fun LoginScreen(viewModel: LoginViewModel?, navController: NavController) {

    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    val passwordVisibility = remember { mutableStateOf(false) }

    val loginFlow = viewModel?.loginFlow?.collectAsState()
    val context = LocalContext.current

    //Set the fields in Login to fill
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .wrapContentSize(Alignment.Center)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            OutlinedTextField(
                value = emailValue,
                onValueChange = { emailValue = it },
                label = { Text(text = "Email") },
                placeholder = { Text(text = "Enter email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.8f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black,
                    focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.Blue,
                    focusedLabelColor = Color.Blue,
                    unfocusedLabelColor = Color.Blue
                )
            )

            OutlinedTextField(
                value = passwordValue,
                onValueChange = { passwordValue = it },
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Enter the password") },
                singleLine = true,
                visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.Black,
                    focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.Blue,
                    focusedLabelColor = Color.Blue,
                    unfocusedLabelColor = Color.Blue
                )
            )

            Spacer(modifier = Modifier.padding(10.dp))
            //In case all works, navigate to Home Screen
            Button(
                onClick = {
                    viewModel?.login(emailValue,passwordValue)
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
            ) {
                Text(
                    text = "Login"
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
                text = "You don't have account? Sign up here!",
                modifier = Modifier.clickable(onClick = {
                    navController.navigate(Screen.SignUpScreen.route)
                }), color = Color.Black, fontSize = 14.sp
            )
        }
    }
    //Make the logic to the database call, evaluating three possible cases:
    // Success in case the call is correct, Failure in case the call is incorrect and Loading
    loginFlow?.value?.let{
        when(it){
            is Resource.Success ->{
                LaunchedEffect(Unit){
                    navController.navigate(BottomNavScreen.Home.screen_route){
                        popUpTo(Screen.LoginScreen.route) {inclusive = true}
                    }
                }
            }
            is Resource.Failure -> {
                LaunchedEffect(loginFlow.value){
                    Toast.makeText(context,it.exception.message, Toast.LENGTH_LONG).show()
                }
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