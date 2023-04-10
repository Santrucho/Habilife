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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.santrucho.habilife.ui.navigation.Screen
import com.santrucho.habilife.ui.presentation.LoginViewModel
import com.santrucho.habilife.ui.ui.bottombar.BottomNavScreen
import com.santrucho.habilife.ui.ui.goals.components.NewFields
import com.santrucho.habilife.ui.ui.goals.components.PasswordFields
import com.santrucho.habilife.ui.util.BackPressHandler
import com.santrucho.habilife.ui.util.HandleState
import com.santrucho.habilife.ui.util.LogBundle


/**
 * Composable who show and structure the Login Screen. Here the user
 * fill the fields with her information and log in to use the app.
 * The user need to be register first to create a email and password for
 * authentication.
 * This is the first screen in the app
 *
 * @param viewModel Use LoginViewModel to get user information and have access to data in the database
 * @param navController Used to navigate between screens
 *
 */

@Composable
fun LoginScreen(viewModel: LoginViewModel, navController: NavController) {

    /**
     * context variable is used to have access to the local context used for Firebase Analytics
     * firebaseAnalytics create a instance to Firebase Analytics for information about user engagement
     */
    val context = LocalContext.current
    val firebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    /**
     * Call the object logBundleAnalytics which is create for have access in the whole app and no create an instance and repeat
     * code everytime when is needed
     * This line is common in the whole app to have this data for engagement
     */
    LogBundle.logBundleAnalytics(firebaseAnalytics, "Login Screen View", "log_in_screen_view")

    //If the user click onBack button of their cellphones, the app will close out
    val onBack = {}
    BackPressHandler(onBackPressed = onBack)

    /*Collect in async way values and get the current user who want to login.
      * This variable is called in HandleState who is in charge to makes this logic
      * and communicate with the backend
     */
    val loginFlow = viewModel.loginFlow.collectAsState()

    //Set the fields and button in Login to fill by the user
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondaryVariant)
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
                    Text(
                        text = "Email",
                        fontSize = 18.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(4.dp)
                    )

                    /*
                       * This functions create a fields who interact with view model
                       * for correct validations and forms.
                       * Is used in some screens to no repeat code and have personalized fields
                       *
                       * This is the field to Email data
                     */

                    NewFields(text = "Email",
                        value = viewModel.emailValue.value,
                        isError = viewModel.isEmailValid.value,
                        error = viewModel.emailErrMsg.value,
                        valueChange = { viewModel.emailValue.value = it }, onValidate = { })

                    Text(
                        text = "Contraseña",
                        fontSize = 18.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(4.dp)
                    )

                    /*
                       * This function create a field who interact with view model
                       * for correct validations and forms to the password.
                       * Is used in some screens to no repeat code and have personalized fields
                       *
                       * This is the field for password data
                     */

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


                    Button(
                        //When the button is pressed, call HandleState and in case to all works navigate the user to Home Screen
                        onClick = {
                            /*
                             * This function call login in View Model who makes the logic to access into the authentication database
                             * for the access into the app
                             *
                             */
                            viewModel.login(
                                viewModel.emailValue.value,
                                viewModel.passwordValue.value
                            )
                            LogBundle.logBundleAnalytics(
                                firebaseAnalytics,
                                "Login Pressed",
                                "log_in_pressed"
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

                    /**
                     * Set the option to navigate to Sign Up Screen,
                     * where the user can create a new account and register in the app
                     */
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = {
                                navController.navigate(Screen.SignUpScreen.route)
                                LogBundle.logBundleAnalytics(
                                    firebaseAnalytics,
                                    "Go to Sign Up",
                                    "go_to_sign_up_pressed"
                                )
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

            /**
             * Make the logic to the database call, evaluating three possible cases:
             * Success in case the call is correct, Failure in case the call is incorrect and Loading to waiting
             */
            HandleState(
                flow = loginFlow,
                navController = navController,
                route = BottomNavScreen.Home.screen_route,
                text = "Se ha iniciado sesion",
                message = "Login",
                eventName = "log_in"
            )
        }
    }
}
