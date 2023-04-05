package com.santrucho.habilife.ui.ui.signup

import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.SignUpViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.ui.goals.components.NewFields
import com.santrucho.habilife.ui.ui.goals.components.PasswordFields
import com.santrucho.habilife.ui.util.HandleState
import com.santrucho.habilife.ui.util.LogBundle


@Composable
fun SignUpScreen(navController: NavController, viewModel: SignUpViewModel) {

    val context = LocalContext.current
    val firebaseAnalytics : FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    LogBundle.logBundleAnalytics(firebaseAnalytics,"Signup Screen View","sign_up_screen_view")


    val signUpFlow = viewModel.signUpFlow.collectAsState()

    //Set the fields in SignUp to fill
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
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
                    Spacer(modifier = Modifier.padding(12.dp))
                    NewFields(text = "Username",
                        value = viewModel.usernameValue.value,
                        isError = viewModel.isUsernameValid.value,
                        error = viewModel.usernameErrMsg.value,
                        valueChange = { viewModel.usernameValue.value = it }, onValidate = { viewModel.validateUsername() })

                    NewFields(text = "Email",
                        value = viewModel.emailValue.value.toString() ?: "",
                        isError = viewModel.isEmailValid.value,
                        error = viewModel.emailErrMsg.value,
                        valueChange = { viewModel.emailValue.value = it }, onValidate = { viewModel.validateEmail() })

                    PasswordFields(
                        text = "Password",
                        value = viewModel.passwordValue,
                        isError = viewModel.isPasswordValid,
                        error = viewModel.passwordErrMsg,
                        passwordVisibility = viewModel.passwordVisibility,
                        valueChange = { it },
                        onValidate = { viewModel.validatePassword() }
                    )
                    PasswordFields(
                        text = "Confirm password",
                        value = viewModel.confirmPasswordValue,
                        isError = viewModel.isConfirmPasswordValid,
                        error = viewModel.confirmPasswordErrMsg,
                        passwordVisibility = viewModel.confirmPasswordVisibility,
                        valueChange = { it },
                        onValidate = { viewModel.confirmPassword() }
                    )
                    Spacer(modifier = Modifier.padding(10.dp))

                    //In case the call is correct, call ViewModel to add user in the database, and navigate to HomeScreen
                    Button(
                        onClick = {
                            viewModel.signUp(
                                viewModel.usernameValue.value,
                                viewModel.emailValue.value,
                                viewModel.passwordValue.value
                            )
                            LogBundle.logBundleAnalytics(firebaseAnalytics,"Register Pressed","register_pressed")
                        },
                        shape = CircleShape,
                        enabled = viewModel.isEnabledConfirmButton.value,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(56.dp)
                            .align(Alignment.CenterHorizontally),
                    ) {
                        Text(
                            text = "Create account"
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable(onClick = {
                            navController.navigate(Screen.LoginScreen.route)
                            LogBundle.logBundleAnalytics(firebaseAnalytics,"Go to Login","go_to_login_pressed")
                        }),
                        horizontalArrangement = Arrangement.Center){
                        Text(
                            text = "You already have an account? ",
                            modifier = Modifier,
                            color = Color.Black,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Sign in",
                            modifier = Modifier,
                            color = Color.Blue,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
            //Make the logic to the database call to add a new user, evaluating three possible cases:
            //Success in case the call is correct, Failure in case the call is incorrect and Loading
            HandleState(
                flow = signUpFlow,
                navController = navController,
                route = BottomNavScreen.Home.screen_route,
                text = "Cuenta creada",
                message = "Sign up",
                eventName = "sign_up"
            )
        }
    }
}
