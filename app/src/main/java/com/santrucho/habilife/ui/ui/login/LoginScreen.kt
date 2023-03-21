package com.santrucho.habilife.ui.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.LoginViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.ui.goals.components.NewFields
import com.santrucho.habilife.ui.ui.goals.components.PasswordFields
import com.santrucho.habilife.ui.util.BackPressHandler
import com.santrucho.habilife.ui.util.HandleState


@Composable
fun LoginScreen(viewModel: LoginViewModel, navController: NavController) {

    val onBack = {}
    BackPressHandler(onBackPressed = onBack)
    viewModel.resetValues()
    val loginFlow = viewModel.loginFlow.collectAsState()
    //Set the fields in Login to fill
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Card(
                shape = MaterialTheme.shapes.medium,
                elevation = 8.dp,
                backgroundColor = MaterialTheme.colors.background,
                modifier = Modifier.padding(16.dp)
            ) {

                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(text = "Email", fontSize = 18.sp, color = Color.Black,modifier = Modifier.padding(4.dp))
                    NewFields(text = "Email",
                        value = viewModel.emailValue.value.toString() ?: "",
                        isError = viewModel.isEmailValid.value,
                        error = viewModel.emailErrMsg.value,
                        valueChange = { viewModel.emailValue.value = it }, onValidate = { })

                    Text(text = "Contrasena", fontSize = 18.sp, color = Color.Black,modifier = Modifier.padding(4.dp))
                    PasswordFields(
                        text = "Password",
                        value = viewModel.passwordValue,
                        isError = viewModel.isPasswordValid,
                        error = viewModel.passwordErrMsg,
                        passwordVisibility = viewModel.passwordVisibility,
                        valueChange = { it },
                        onValidate = {}
                    )

                    Spacer(modifier = Modifier.padding(16.dp))
                    //In case all works, navigate to Home Screen
                    Button(
                        onClick = {
                            viewModel.login(
                                viewModel.emailValue.value,
                                viewModel.passwordValue.value
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(56.dp)
                            .align(Alignment.CenterHorizontally),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Login"
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = {
                                navController.navigate(Screen.SignUpScreen.route)
                            }),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "You don't have account? ",
                            modifier = Modifier,
                            color = Color.Black,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "SignUp here!",
                            modifier = Modifier,
                            color = Color.Blue,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
            //Make the logic to the database call, evaluating three possible cases:
            // Success in case the call is correct, Failure in case the call is incorrect and Loading
            HandleState(
                flow = loginFlow,
                navController = navController,
                route = BottomNavScreen.Home.screen_route,
                text = "Se ha iniciado sesion"
            )
        }
    }
}
